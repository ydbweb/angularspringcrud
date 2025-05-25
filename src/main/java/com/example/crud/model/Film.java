package com.example.crud.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year; // For 'year' type
import java.util.Set; // For many-to-many relationship

/**
 * JPA Entity for the 'film' table.
 * Note: 'special_features' (SET type) is simplified to a String.
 * 'rating' (ENUM type) is simplified to a String.
 * Foreign keys `language_id` and `original_language_id` are represented as Longs.
 */
@Entity
@Table(name = "film")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_year")
    private Year releaseYear; // Using java.time.Year

    @Column(name = "language_id", nullable = false)
    private Long languageId; // Foreign key to language table

    @Column(name = "original_language_id")
    private Long originalLanguageId; // Foreign key to language table, nullable

    @Column(name = "rental_duration", nullable = false)
    private Integer rentalDuration;

    @Column(name = "rental_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal rentalRate;

    @Column(name = "length")
    private Integer length;

    @Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2)
    private BigDecimal replacementCost;

    @Column(name = "rating", columnDefinition = "ENUM('G','PG','PG-13','R','NC-17')")
    private String rating; // Storing ENUM as String

    @Column(name = "special_features", columnDefinition = "SET('Trailers','Commentaries','Deleted Scenes','Behind the Scenes')")
    private String specialFeatures; // Storing SET as String (e.g., "Trailers,Commentaries")

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    // Custom constructor for creating new films without an ID
    public Film(String title, String description, Year releaseYear, Long languageId, Long originalLanguageId,
                Integer rentalDuration, BigDecimal rentalRate, Integer length, BigDecimal replacementCost,
                String rating, String specialFeatures) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.languageId = languageId;
        this.originalLanguageId = originalLanguageId;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.specialFeatures = specialFeatures;
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
}
