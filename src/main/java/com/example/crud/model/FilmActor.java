package com.example.crud.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * JPA Entity for the 'film_actor' join table.
 * Represents the many-to-many relationship between Film and Actor.
 * Uses an @EmbeddedId for the composite primary key.
 */
@Entity
@Table(name = "film_actor")
@Data
@NoArgsConstructor
public class FilmActor {

    @EmbeddedId
    private FilmActorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("actorId") // Maps the 'actorId' field of the embedded ID
    @JoinColumn(name = "actor_id")
    private Actor actor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("filmId") // Maps the 'filmId' field of the embedded ID
    @JoinColumn(name = "film_id")
    private Film film;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    // Constructor for convenience
    public FilmActor(Actor actor, Film film) {
        this.actor = actor;
        this.film = film;
        this.id = new FilmActorId(actor.getActorId(), film.getFilmId());
        this.lastUpdate = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        lastUpdate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }

    /**
     * EmbeddedId class for FilmActor composite primary key.
     * Must implement Serializable and override equals() and hashCode().
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilmActorId implements Serializable {
        @Column(name = "actor_id")
        private Long actorId;

        @Column(name = "film_id")
        private Long filmId;
    }
}
