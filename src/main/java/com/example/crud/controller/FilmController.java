package com.example.crud.controller;

import com.example.crud.model.Film;
import com.example.crud.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Film entities.
 * Handles HTTP requests related to Film CRUD operations.
 */
@RestController
@RequestMapping("/api/films") // Base path for all film-related endpoints
public class FilmController {

    private final FilmService filmService;

    /**
     * Constructor injection for FilmService.
     * @param filmService The service for Film entities.
     */
    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    /**
     * GET /api/films : Get all films.
     * @return ResponseEntity with a list of Film objects and HTTP status OK.
     */
    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> films = filmService.getAllFilms();
        return new ResponseEntity<>(films, HttpStatus.OK);
    }

    /**
     * GET /api/films/{id} : Get film by ID.
     * @param id The ID of the film to retrieve.
     * @return ResponseEntity with the Film object if found, or HTTP status NOT_FOUND.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id)
                .map(film -> new ResponseEntity<>(film, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST /api/films : Create a new film.
     * @param film The Film object to create.
     * @return ResponseEntity with the created Film object and HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        // Ensure filmId is null for creation to let JPA generate it
        film.setFilmId(null);
        Film savedFilm = filmService.saveFilm(film);
        return new ResponseEntity<>(savedFilm, HttpStatus.CREATED);
    }

    /**
     * PUT /api/films/{id} : Update an existing film.
     * @param id The ID of the film to update.
     * @param filmDetails The updated Film object.
     * @return ResponseEntity with the updated Film object, or HTTP status NOT_FOUND if film does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Long id, @RequestBody Film filmDetails) {
        return filmService.getFilmById(id)
                .map(existingFilm -> {
                    // Update fields from filmDetails
                    existingFilm.setTitle(filmDetails.getTitle());
                    existingFilm.setDescription(filmDetails.getDescription());
                    existingFilm.setReleaseYear(filmDetails.getReleaseYear());
                    existingFilm.setLanguageId(filmDetails.getLanguageId());
                    existingFilm.setOriginalLanguageId(filmDetails.getOriginalLanguageId());
                    existingFilm.setRentalDuration(filmDetails.getRentalDuration());
                    existingFilm.setRentalRate(filmDetails.getRentalRate());
                    existingFilm.setLength(filmDetails.getLength());
                    existingFilm.setReplacementCost(filmDetails.getReplacementCost());
                    existingFilm.setRating(filmDetails.getRating());
                    existingFilm.setSpecialFeatures(filmDetails.getSpecialFeatures());
                    // lastUpdate is handled by @PreUpdate in the entity
                    Film updatedFilm = filmService.saveFilm(existingFilm);
                    return new ResponseEntity<>(updatedFilm, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /api/films/{id} : Delete a film by ID.
     * @param id The ID of the film to delete.
     * @return ResponseEntity with HTTP status NO_CONTENT if deleted, or NOT_FOUND if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFilm(@PathVariable Long id) {
        boolean deleted = filmService.deleteFilm(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}