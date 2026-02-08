package com.example.crud.service;

import com.example.crud.model.Film;
import com.example.crud.repository.FilmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllFilms() {
        List<Film> films = Arrays.asList(new Film(), new Film());
        when(filmRepository.findAll()).thenReturn(films);

        List<Film> result = filmService.getAllFilms();

        assertEquals(2, result.size());
        verify(filmRepository, times(1)).findAll();
    }

    @Test
    void testGetFilmById() {
        Film film = new Film();
        when(filmRepository.findById(1)).thenReturn(Optional.of(film));

        Optional<Film> result = filmService.getFilmById(1);

        assertTrue(result.isPresent());
        assertEquals(film, result.get());
        verify(filmRepository, times(1)).findById(1);
    }

    @Test
    void testSaveFilm() {
        Film film = new Film();
        when(filmRepository.save(film)).thenReturn(film);

        Film result = filmService.saveFilm(film);

        assertEquals(film, result);
        verify(filmRepository, times(1)).save(film);
    }

    @Test
    void testDeleteFilm() {
        when(filmRepository.existsById(1)).thenReturn(true);

        boolean result = filmService.deleteFilm(1);

        assertTrue(result);
        verify(filmRepository, times(1)).existsById(1);
        verify(filmRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteFilmNotFound() {
        when(filmRepository.existsById(1)).thenReturn(false);

        boolean result = filmService.deleteFilm(1);

        assertFalse(result);
        verify(filmRepository, times(1)).existsById(1);
        verify(filmRepository, never()).deleteById(1);
    }
}