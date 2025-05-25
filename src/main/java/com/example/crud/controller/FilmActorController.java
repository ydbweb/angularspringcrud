package com.example.crud.controller;

import com.example.crud.model.FilmActor;
import com.example.crud.service.FilmActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for FilmActor entities.
 * Handles HTTP requests related to FilmActor relationships.
 */
@RestController
@RequestMapping("/api/film-actors") // Base path for all film-actor related endpoints
public class FilmActorController {

    private final FilmActorService filmActorService;

    @Autowired
    public FilmActorController(FilmActorService filmActorService) {
        this.filmActorService = filmActorService;
    }

    /**
     * GET /api/film-actors : Get all film-actor relationships.
     * @return ResponseEntity with a list of FilmActor objects and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<FilmActor>> getAllFilmActors() {
        List<FilmActor> filmActors = filmActorService.getAllFilmActors();
        return new ResponseEntity<>(filmActors, HttpStatus.OK);
    }

    /**
     * GET /api/film-actors/{actorId}/{filmId} : Get a specific film-actor relationship by composite ID.
     * @param actorId The ID of the actor.
     * @param filmId The ID of the film.
     * @return ResponseEntity with the FilmActor object if found, or HTTP status NOT_FOUND.
     */
    @GetMapping("/{actorId}/{filmId}")
    public ResponseEntity<FilmActor> getFilmActorById(@PathVariable Long actorId, @PathVariable Long filmId) {
        Optional<FilmActor> filmActor = filmActorService.getFilmActorById(actorId, filmId);
        return filmActor
                .map(fa -> new ResponseEntity<>(fa, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST /api/film-actors : Create a new film-actor relationship.
     * Expects actorId and filmId in the request body.
     * @param payload A FilmActorRequest object containing "actorId" and "filmId".
     * @return ResponseEntity with the created FilmActor object and HTTP status CREATED,
     * or BAD_REQUEST if actor/film not found.
     */
    @PostMapping
    public ResponseEntity<FilmActor> createFilmActor(@RequestBody FilmActorRequest payload) {
        FilmActor createdFilmActor = filmActorService.createFilmActor(payload.getActorId(), payload.getFilmId());
        if (createdFilmActor != null) {
            return new ResponseEntity<>(createdFilmActor, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Or a more specific error message
        }
    }

    /**
     * DELETE /api/film-actors/{actorId}/{filmId} : Delete a film-actor relationship by composite ID.
     * @param actorId The ID of the actor.
     * @param filmId The ID of the film.
     * @return ResponseEntity with HTTP status NO_CONTENT if deleted, or NOT_FOUND if not found.
     */
    @DeleteMapping("/{actorId}/{filmId}")
    public ResponseEntity<HttpStatus> deleteFilmActor(@PathVariable Long actorId, @PathVariable Long filmId) {
        boolean deleted = filmActorService.deleteFilmActor(actorId, filmId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}