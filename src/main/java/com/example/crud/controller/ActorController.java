package com.example.crud.controller;

import com.example.crud.model.Actor;
import com.example.crud.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Actor entities.
 * Handles HTTP requests related to Actor CRUD operations.
 */
@RestController
@RequestMapping("/api/actors") // Base path for all actor-related endpoints
public class ActorController {

    private final ActorService actorService;

    /**
     * Constructor injection for ActorService.
     * @param actorService The service for Actor entities.
     */
    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    /**
     * GET /api/actors : Get all actors.
     * @return ResponseEntity with a list of Actor objects and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<Actor>> getAllActors() {
        List<Actor> actors = actorService.getAllActors();
        return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    /**
     * GET /api/actors/{id} : Get actor by ID.
     * @param id The ID of the actor to retrieve.
     * @return ResponseEntity with the Actor object if found, or HTTP status NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Long id) {
        return actorService.getActorById(id)
                .map(actor -> new ResponseEntity<>(actor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST /api/actors : Create a new actor.
     * @param actor The Actor object to create.
     * @return ResponseEntity with the created Actor object and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
        // Ensure actorId is null for creation to let JPA generate it
        actor.setActorId(null);
        Actor savedActor = actorService.saveActor(actor);
        return new ResponseEntity<>(savedActor, HttpStatus.CREATED);
    }

    /**
     * PUT /api/actors/{id} : Update an existing actor.
     * @param id The ID of the actor to update.
     * @param actorDetails The updated Actor object.
     * @return ResponseEntity with the updated Actor object, or HTTP status NOT_FOUND if actor does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor actorDetails) {
        return actorService.getActorById(id)
                .map(existingActor -> {
                    // Update fields from actorDetails
                    existingActor.setFirstName(actorDetails.getFirstName());
                    existingActor.setLastName(actorDetails.getLastName());
                    // lastUpdate is handled by @PreUpdate in the entity
                    Actor updatedActor = actorService.saveActor(existingActor);
                    return new ResponseEntity<>(updatedActor, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /api/actors/{id} : Delete an actor by ID.
     * @param id The ID of the actor to delete.
     * @return ResponseEntity with HTTP status NO_CONTENT if deleted, or NOT_FOUND if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteActor(@PathVariable Long id) {
        boolean deleted = actorService.deleteActor(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
