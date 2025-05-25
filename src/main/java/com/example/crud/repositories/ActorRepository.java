package com.example.crud.repository;

import com.example.crud.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA Repository for the Actor entity.
 * Provides standard CRUD operations out-of-the-box.
 * The first generic parameter is the entity type, the second is the ID type.
 */
@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    // Spring Data JPA automatically provides methods like findAll(), findById(), save(), deleteById()
    // You can add custom query methods here if needed, e.g., findByLastName(String lastName);
}