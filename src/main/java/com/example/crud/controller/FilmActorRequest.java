package com.example.crud.controller; 
import java.util.Objects;

public class FilmActorRequest {
    private Long actorId;
    private Long filmId;

    public FilmActorRequest() {
    }

    public FilmActorRequest(Long actorId, Long filmId) {
        this.actorId = actorId;
        this.filmId = filmId;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmActorRequest that = (FilmActorRequest) o;
        return Objects.equals(actorId, that.actorId) && Objects.equals(filmId, that.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, filmId);
    }

    @Override
    public String toString() {
        return "FilmActorRequest{" +
                "actorId=" + actorId +
                ", filmId=" + filmId +
                '}';
    }
}