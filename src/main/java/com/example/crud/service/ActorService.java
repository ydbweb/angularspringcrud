package com.example.crud.service;

import com.example.crud.model.Actor;
import com.example.crud.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing Actor entities.
 * Contains business logic and interacts with the ActorRepository.
 */
@Service
public class ActorService {

    private final ActorRepository actorRepository;

    /**
     * Constructor injection for ActorRepository.
     * Spring automatically injects the ActorRepository instance.
     * @param actorRepository The repository for Actor entities.
     */
    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    /**
     * Retrieves all actors from the database.
     * @return A list of all Actor entities.
     */
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    /**
     * Retrieves an actor by their ID.
     * @param id The ID of the actor to retrieve.
     * @return An Optional containing the Actor if found, or empty if not found.
     */
    public Optional<Actor> getActorById(Long id) {
        return actorRepository.findById(id);
    }

    /**
     * Creates a new actor or updates an existing one.
     * If the actor object has an ID, it attempts to update.
     * If the actor object does not have an ID, it creates a new one.
     * @param actor The Actor entity to save or update.
     * @return The saved or updated Actor entity.
     */
    public Actor saveActor(Actor actor) {
        // For new actors, the lastUpdate timestamp will be set by @PrePersist.
        // For existing actors, it will be updated by @PreUpdate.
        return actorRepository.save(actor);
    }

    /**
     * Deletes an actor by their ID.
     * @param id The ID of the actor to delete.
     * @return true if the actor was found and deleted, false otherwise.
     */
    public boolean deleteActor(Long id) {
        if (actorRepository.existsById(id)) {
            actorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
