package com.example.crud.controller;

import com.example.crud.model.Film;
import com.example.crud.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmactor/api/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Film>> getAllFilms() {
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Integer id) {
        return filmService.getFilmById(id)
                .map(film -> new ResponseEntity<>(film, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        film.setFilmId(null);
        return new ResponseEntity<>(filmService.saveFilm(film), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable Integer id, @RequestBody Film filmDetails) {
        return filmService.getFilmById(id)
                .map(existingFilm -> {
                    existingFilm.setTitle(filmDetails.getTitle());
                    existingFilm.setDescription(filmDetails.getDescription());
                    existingFilm.setReleaseYear(filmDetails.getReleaseYear());
                   // existingFilm.setLanguageId(filmDetails.getLanguageId());
                   // existingFilm.setOriginalLanguageId(filmDetails.getOriginalLanguageId());
                    existingFilm.setRentalDuration(filmDetails.getRentalDuration());
                    existingFilm.setRentalRate(filmDetails.getRentalRate());
                    existingFilm.setLength(filmDetails.getLength());
                    existingFilm.setReplacementCost(filmDetails.getReplacementCost());
                    existingFilm.setRating(filmDetails.getRating());
                    existingFilm.setLastUpdate(filmDetails.getLastUpdate());
                    return new ResponseEntity<>(filmService.saveFilm(existingFilm), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFilm(@PathVariable Integer id) {
        if (filmService.deleteFilm(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
