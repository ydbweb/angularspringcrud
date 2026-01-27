package com.example.crud.repository;

import com.example.crud.model.Actor;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	@Modifying
	@Query(value = "DELETE FROM film_actor WHERE actor_id = :actorId AND film_id = :filmId", nativeQuery = true)
	void removeFilmAssociation(@Param("actorId") Short actorId, @Param("filmId") Integer filmId);
	
	@Modifying
	@Query(value = "INSERT INTO film_actor (actor_id,film_id) VALUES (:actorId,:filmId)", nativeQuery = true)
	void addFilmAssociation(@Param("actorId") Short actorId, @Param("filmId") Integer filmId);	

		
}
