package com.jhipster.sample.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Point.
 */
@Entity
@Table(name = "point")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Point implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @NotNull
    @Column(name = "exercise", nullable = false)
    private Boolean exercise;
    
    @NotNull
    @Column(name = "meal", nullable = false)
    private Boolean meal;
    
    @NotNull
    @Column(name = "alcohol", nullable = false)
    private Boolean alcohol;
    
    @Column(name = "notes")
    private String notes;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getExercise() {
        return exercise;
    }
    
    public void setExercise(Boolean exercise) {
        this.exercise = exercise;
    }

    public Boolean getMeal() {
        return meal;
    }
    
    public void setMeal(Boolean meal) {
        this.meal = meal;
    }

    public Boolean getAlcohol() {
        return alcohol;
    }
    
    public void setAlcohol(Boolean alcohol) {
        this.alcohol = alcohol;
    }

    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        if(point.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, point.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Point{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", exercise='" + exercise + "'" +
            ", meal='" + meal + "'" +
            ", alcohol='" + alcohol + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
