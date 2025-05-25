package com.example.crud.repository;

import com.example.crud.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for the Film entity.
 * Provides standard CRUD operations for Film entities.
 */
@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    // Standard CRUD methods are inherited from JpaRepository
}
