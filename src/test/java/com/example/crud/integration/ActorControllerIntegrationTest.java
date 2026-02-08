package com.example.crud.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.crud.model.Actor;
import com.example.crud.model.Film;
import com.example.crud.service.ActorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.UUID;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActorControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Mock
    ActorService actorService;
    
    
    private Actor actor;
    
    private ArrayList filmsarray;


    @BeforeAll
    void setup() {
    	
    	Film film1=new Film();
    	Film film2=new Film();
    	
    	film1.setFilmId((short)1);
    	film2.setFilmId((short)2);
    	
    	film1.setTitle("Film 1");
    	film2.setTitle("Film 2");
    	
    	filmsarray=new java.util.ArrayList<>(java.util.List.of(film1, film2));
    	
    	
    	actor = new Actor();
    	actor.setActorId((short)1);
    	actor.setFirstName("NICK");
    	actor.setLastName("WAHLBERG");
    	actor.setLastUpdate(null);
    	actor.setFilms(new java.util.ArrayList<>(java.util.List.of(film1, film2)));
    	
    	
    	/*
    	// Create a unique actor for testing
		String uniqueFirstName = "NICK_" + UUID.randomUUID();
		String uniqueLastName = "WAHLBERG_" + UUID.randomUUID();
		
		String newActorJson = String.format("{\"firstName\": \"%s\", \"lastName\": \"%s\"}", uniqueFirstName, uniqueLastName);
		
		try {
			mockMvc.perform(post("/filmactor/api/actors")
					.contentType(MediaType.APPLICATION_JSON)
					.content(newActorJson))
					.andExpect(status().isCreated())
					.andExpect(jsonPath("$.firstName", is(uniqueFirstName)))
					.andExpect(jsonPath("$.lastName", is(uniqueLastName)));
		} catch (Exception e) {
			e.printStackTrace();

    }
    */
    }

    @Test
    void testlistactors() throws Exception {
        mockMvc.perform(get("/filmactor/api/actors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[?(@.firstName == '" + "NICK" + "')]").exists());
    }
    
    @Test
    void testgetactor() throws Exception {
        mockMvc.perform(get("/filmactor/api/actors/actorid/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    void updateActortest() throws Exception {
    	
    	when(actorService.updateActor(1, any(Actor.class))).thenReturn(this.actor);
    	
		mockMvc.perform(put("/filmactor/api/actors/2")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(objectMapper.writeValueAsString(this.actor)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    } 
    
    @PutMapping("/addtoactor/{id}")
    void addUpdateToactor(@PathVariable Integer id, @RequestBody Object actorDetails) throws JsonProcessingException, Exception {
    	
    	when(actorService.addUpdateToactorFilms(2, actorDetails)).thenReturn(this.actor);
    	
		mockMvc.perform(put("/filmactor/api/actors/addtoactor/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(this.filmsarray)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    

}