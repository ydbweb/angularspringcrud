package com.example.crud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.crud.model.Actor;
import com.example.crud.model.Film;
import com.example.crud.repository.ActorRepository;
import com.example.crud.repository.FilmRepository;

import jakarta.persistence.EntityManager;



@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ActorServiceTest {

	@Mock
	ActorRepository actorRepository;
	
	@Mock
	FilmRepository filmRepository;
	
	@Mock
    private EntityManager entityManager;	
	
	@InjectMocks
	ActorService actorService;
	
	Actor actor;
	
	@BeforeEach
	void setup() {
		Film film1=new Film();
		Film film2=new Film();
		
		film1.setFilmId((short)1);
		film2.setFilmId((short)2);
		
		actor = new Actor();
		actor.setActorId((short)1);
		actor.setFirstName("John");
		actor.setLastName("Doe");
		
		actor.setFilms(new ArrayList<>(List.of(film1, film2)));
	}
	
	@Test
	@DisplayName("Test getAllActors returns correct number of actors")
	void getlistofactors() {
		when(actorRepository.findAll()).thenReturn(List.of(new Actor(), new Actor()));	
		assertEquals(2, actorService.getAllActors().size());
	}
	

    
	@Test
	@DisplayName("get films from a specific actor")
	void getfilmsfromactor() {
		Integer actor_id = 1;
		when(actorRepository.findById(actor_id)).thenReturn(Optional.of(actor));	
		assertThrows(RuntimeException.class,() -> actorService.getFilmsFromActor(-1));
		assertEquals(actorService.getFilmsFromActor(actor_id).size(),actor.getFilms().size());		
	}	
	
	@Test
	@DisplayName("get single actor")
	void getsingleactor() {
		Integer actor_id = 1;
		when(actorRepository.findById(actor_id)).thenReturn(Optional.of(actor));	
		assertTrue(actorService.getActorById(actor_id).isPresent());		
	}	
	
	@Test
	@DisplayName("save single actor")	
    public void saveActor() {
		when(actorRepository.save(actor)).thenReturn(actor);
		assertTrue(actorService.saveActor(actor) instanceof Actor);
    }	
	
	@Test
	@DisplayName("update single actor")	
    public void updateActor() {
    	Integer id = 1;	
    	Object actorDetails;
    		
    	actorDetails = new HashMap<>();
		((Map<String, Object>) actorDetails).put("filmsform", "[1,2]");
		((Map<String, Object>) actorDetails).put("filmsformactor", "[3]");
		((Map<String, Object>) actorDetails).put("firstName", "Jane");
		((Map<String, Object>) actorDetails).put("lastName", "Smith");
		
		when(actorRepository.findById(id)).thenReturn(Optional.of(actor));	
		assertThrows(RuntimeException.class,() -> actorService.getFilmsFromActor(-1));
		when(actorRepository.save(actor)).thenReturn(actor);
		
		assertTrue(actorService.updateActor(id, actorDetails) instanceof Actor);
		assertEquals("Jane", actorService.updateActor(id, actorDetails).getFirstName());
		assertEquals("Smith", actorService.updateActor(id, actorDetails).getLastName());		
	}
	
	@Test
	@DisplayName("update film list for actors")	
    public void addUpdateToActorFilms() {
	    Integer id = 1;	
	    Map<String, Object> actorDetails = new HashMap<>();
	    actorDetails.put("filmsform", null); // Films to èadd
	    actorDetails.put("filmsformactor", new ArrayList<>(List.of(1)));
	    // ... other puts

	    when(actorRepository.findById(id)).thenReturn(Optional.of(this.actor));	
	    when(actorRepository.save(this.actor)).thenReturn(this.actor);	    
	    lenient().doNothing().when(entityManager).flush();
	    lenient().doNothing().when(entityManager).refresh(actor);
	    
	    // 2. Act
	    Actor result = actorService.addUpdateToactorFilms(id, actorDetails);
	    
	    // 3. Assert
	    assertNotNull(result);
	    System.out.println("Films associated with actor after update: " + result.getFilms().get(1).getFilmId()+"b");
	    // If your service logic adds the 2 films from the map:
	    assertEquals(2, result.getFilms().size());
	    verify(actorRepository, times(1)).removeFilmAssociation(this.actor.getActorId(), 1);
	}
	
	@Test
	@DisplayName("Test delete actor")
	void deleteactor() {
		doNothing().when(actorRepository).deleteById(14); 
		assertFalse(actorService.deleteActor(14));
	}	

}
