package com.lukaklacar.codesnip.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.lukaklacar.codesnip.domain.enumeration.RatingType;

/**
 * A Rating.
 */
@Entity
@Table(name = "rating")
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rating_type", nullable = false)
    private RatingType ratingType;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Snippet snippet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RatingType getRatingType() {
        return ratingType;
    }

    public Rating ratingType(RatingType ratingType) {
        this.ratingType = ratingType;
        return this;
    }

    public void setRatingType(RatingType ratingType) {
        this.ratingType = ratingType;
    }

    public User getOwner() {
        return owner;
    }

    public Rating owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public Rating snippet(Snippet snippet) {
        this.snippet = snippet;
        return this;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rating rating = (Rating) o;
        if (rating.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rating.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Rating{" +
            "id=" + id +
            ", ratingType='" + ratingType + "'" +
            '}';
    }
}
