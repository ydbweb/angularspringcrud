package com.example.crud.repository;

import com.example.crud.model.FilmActor;
import com.example.crud.model.FilmActor.FilmActorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository for the FilmActor entity.
 * Uses FilmActor.FilmActorId as the primary key type.
 */
@Repository
public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorId> {
    // Custom query methods can be added here if needed, e.g.:
    List<FilmActor> findByIdActorId(Long actorId);
    List<FilmActor> findByIdFilmId(Long filmId);
}
