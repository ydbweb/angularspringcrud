package com.example.crud.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set; // For many-to-many relationship

/**
 * JPA Entity for the 'actor' table.
 * Uses Lombok annotations for boilerplate code (getters, setters, constructors).
 */
@Entity
@Table(name = "actor")
@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing primary key
    @Column(name = "actor_id")
    private Long actorId;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "last_update", nullable = false)
    // Using LocalDateTime for timestamp columns
    private LocalDateTime lastUpdate;

    // Custom constructor for creating new actors without an ID (ID is auto-generated)
    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastUpdate = LocalDateTime.now(); // Set current timestamp on creation
    }

    // PrePersist and PreUpdate annotations to automatically manage lastUpdate timestamp
    @PrePersist
    protected void onCreate() {
        lastUpdate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}