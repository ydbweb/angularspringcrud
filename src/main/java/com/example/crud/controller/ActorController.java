package com.example.crud.controller;

import com.example.crud.model.Actor;
import com.example.crud.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {
    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public ResponseEntity<List<Actor>> getAllActors() {
        return new ResponseEntity<>(actorService.getAllActors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable Integer id) {
        return actorService.getActorById(id)
                .map(actor -> new ResponseEntity<>(actor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
        actor.setActorId(null);
        return new ResponseEntity<>(actorService.saveActor(actor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Integer id, @RequestBody Actor actorDetails) {
        return actorService.getActorById(id)
                .map(existingActor -> {
                    existingActor.setFirstName(actorDetails.getFirstName());
                    existingActor.setLastName(actorDetails.getLastName());
                    existingActor.setLastUpdate(actorDetails.getLastUpdate());
                    return new ResponseEntity<>(actorService.saveActor(existingActor), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteActor(@PathVariable Integer id) {
        if (actorService.deleteActor(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
