package com.example.crud.service;

import com.example.crud.model.Actor;
import com.example.crud.model.Film;
import com.example.crud.repository.ActorRepository;
import com.example.crud.repository.FilmRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ActorService {
    private final ActorRepository actorRepository;
    private final FilmRepository filmRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository, FilmRepository filmRepository) {
        this.actorRepository = actorRepository;
		this.filmRepository = filmRepository;
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }
    
    public List<Film> getFilmsFromActor(Integer id) {
    	Actor actor = actorRepository.findById(id).orElseThrow(() -> new RuntimeException("Actor not found"));
    	System.out.println(actor.getFilms());
        return actor.getFilms();
    }    

    public Optional<Actor> getActorById(Integer id) {
        return actorRepository.findById(id);
    }

    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }
    
    public Actor updateActor(Integer id, Object actorDetails) {
    	Actor actor= actorRepository.findById(id).orElseThrow(() -> new RuntimeException("Actor not found"));
		Map<String, Object> actorMap = new HashMap<>();

		actorMap= (Map<String, Object>) actorDetails;
		
        String firstname = (String) actorMap.get("firstName");
        String lastname = (String) actorMap.get("lastName");
        
        actor.setFirstName(firstname);
        actor.setLastName(lastname);
        
		actorRepository.save(actor);
    	return actor;

	}
    
    @PersistenceContext private EntityManager entityManager;
    
    @Transactional
    public Actor addUpdateToactorFilms(Integer id, Object actorDetails) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new RuntimeException("Actor not found"));
        Map<String, Object> actorMap = (Map<String, Object>) actorDetails;
        
        // --- 1. HANDLE ADDITIONS ---
        List<Integer> filmsToAdd = (List<Integer>) actorMap.get("filmsform");
        if (filmsToAdd != null) {
            for (Integer filmId : filmsToAdd) {
                actor.getFilms().removeIf(f -> f.getFilmId().equals(filmId));
                actorRepository.addFilmAssociation(actor.getActorId(), filmId);
            }
            entityManager.flush(); 
            entityManager.refresh(actor);
        }
        
        // --- 2. HANDLE REMOVALS ---
        List<Integer> filmsToRemove = (List<Integer>) actorMap.get("filmsformactor");
        if (filmsToRemove != null) {
            for (Integer filmId : filmsToRemove) {
                actor.getFilms().removeIf(f -> f.getFilmId().equals(filmId));
                actorRepository.removeFilmAssociation(actor.getActorId(), filmId);
            }
            // Sync everything
            entityManager.flush(); 
            entityManager.refresh(actor);
        }
        
        return actor;
    }  

    public boolean deleteActor(Integer id) {
        if (actorRepository.existsById(id)) {
            actorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
