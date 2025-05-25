package com.example.crud.service;

import com.example.crud.model.Film;
import com.example.crud.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing Film entities.
 * Contains business logic and interacts with the FilmRepository.
 */
@Service
public class FilmService {

    private final FilmRepository filmRepository;

    /**
     * Constructor injection for FilmRepository.
     * @param filmRepository The repository for Film entities.
     */
    @Autowired
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    /**
     * Retrieves all films from the database.
     * @return A list of all Film entities.
     */
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    /**
     * Retrieves a film by its ID.
     * @param id The ID of the film to retrieve.
     * @return An Optional containing the Film if found, or empty if not found.
     */
    public Optional<Film> getFilmById(Long id) {
        return filmRepository.findById(id);
    }

    /**
     * Creates a new film or updates an existing one.
     * @param film The Film entity to save or update.
     * @return The saved or updated Film entity.
     */
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    /**
     * Deletes a film by its ID.
     * @param id The ID of the film to delete.
     * @return true if the film was found and deleted, false otherwise.
     */
    public boolean deleteFilm(Long id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
