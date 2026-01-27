package com.example.crud.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@Entity
@Table(name = "film")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short filmId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id", nullable = false)
    @JsonUnwrapped(enabled = false) // Ensures it doesn't flatten into the parent
    private Language language;

    @Column(name = "rental_duration", nullable = false)
    private Integer rentalDuration;

    @Column(name = "rental_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal rentalRate;

    @Column(name = "length")
    private Integer length;

    @Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2)
    private BigDecimal replacementCost;

    @Column(name = "rating", length = 10)
    private String rating;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "films")
    Set<Actor> actors;
       

    public Short getFilmId() { return filmId; }
    public void setFilmId(Short filmId) { this.filmId = filmId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }
    public Integer getRentalDuration() { return rentalDuration; }
    public void setRentalDuration(Integer rentalDuration) { this.rentalDuration = rentalDuration; }
    public BigDecimal getRentalRate() { return rentalRate; }
    public void setRentalRate(BigDecimal rentalRate) { this.rentalRate = rentalRate; }
    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }
    public BigDecimal getReplacementCost() { return replacementCost; }
    public void setReplacementCost(BigDecimal replacementCost) { this.replacementCost = replacementCost; }
    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
    public LocalDateTime getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(LocalDateTime lastUpdate) { this.lastUpdate = lastUpdate; }
	public Set<Actor> getActors() {
		return actors;
	}
	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	
    
}
