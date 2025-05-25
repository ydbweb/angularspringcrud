package com.example.crud.service;

import com.example.crud.model.FilmActor;
import com.example.crud.model.FilmActor.FilmActorId;
import com.example.crud.repository.ActorRepository;
import com.example.crud.repository.FilmActorRepository;
import com.example.crud.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing FilmActor entities.
 * Handles business logic related to the many-to-many relationship between films and actors.
 */
@Service
public class FilmActorService {

    private final FilmActorRepository filmActorRepository;
    private final ActorRepository actorRepository; // Needed to fetch Actor for FilmActor creation
    private final FilmRepository filmRepository;   // Needed to fetch Film for FilmActor creation

    @Autowired
    public FilmActorService(FilmActorRepository filmActorRepository,
                            ActorRepository actorRepository,
                            FilmRepository filmRepository) {
        this.filmActorRepository = filmActorRepository;
        this.actorRepository = actorRepository;
        this.filmRepository = filmRepository;
    }

    /**
     * Retrieves all film-actor relationships.
     * @return A list of all FilmActor entities.
     */
    public List<FilmActor> getAllFilmActors() {
        return filmActorRepository.findAll();
    }

    /**
     * Retrieves a specific film-actor relationship by its composite ID.
     * @param actorId The ID of the actor.
     * @param filmId The ID of the film.
     * @return An Optional containing the FilmActor if found, or empty if not found.
     */
    public Optional<FilmActor> getFilmActorById(Long actorId, Long filmId) {
        FilmActorId id = new FilmActorId(actorId, filmId);
        return filmActorRepository.findById(id);
    }

    /**
     * Creates a new film-actor relationship.
     * Before creating, it verifies if the actor and film exist.
     * @param actorId The ID of the actor to associate.
     * @param filmId The ID of the film to associate.
     * @return The created FilmActor entity, or null if actor or film not found.
     */
    public FilmActor createFilmActor(Long actorId, Long filmId) {
        return actorRepository.findById(actorId).flatMap(actor ->
                filmRepository.findById(filmId).map(film -> {
                    FilmActor newFilmActor = new FilmActor(actor, film);
                    return filmActorRepository.save(newFilmActor);
                })
        ).orElse(null);
    }

    /**
     * Deletes a film-actor relationship by its composite ID.
     * @param actorId The ID of the actor.
     * @param filmId The ID of the film.
     * @return true if the relationship was found and deleted, false otherwise.
     */
    public boolean deleteFilmActor(Long actorId, Long filmId) {
        FilmActorId id = new FilmActorId(actorId, filmId);
        if (filmActorRepository.existsById(id)) {
            filmActorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Retrieves all films associated with a specific actor.
     * @param actorId The ID of the actor.
     * @return A list of FilmActor entities for the given actor.
     */
    public List<FilmActor> getFilmsByActorId(Long actorId) {
        return filmActorRepository.findByIdActorId(actorId);
    }

    /**
     * Retrieves all actors associated with a specific film.
     * @param filmId The ID of the film.
     * @return A list of FilmActor entities for the given film.
     */
    public List<FilmActor> getActorsByFilmId(Long filmId) {
        return filmActorRepository.findByIdFilmId(filmId);
    }
}
