package com.lukaklacar.codesnip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Snippet.
 */
@Entity
@Table(name = "snippet")
public class Snippet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "snippet", nullable = false)
    private String snippet;

    @Column(name = "url")
    private String url;

    @NotNull
    @Min(value = 1)
    @Column(name = "duration_in_minutes", nullable = false)
    private Integer durationInMinutes;

    @Column(name = "comments_blocked")
    private Boolean commentsBlocked;

    @OneToMany(mappedBy = "snippet")
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "snippet")
    @JsonIgnore
    private Set<Rating> ratings = new HashSet<>();

    @ManyToOne
    private User owner;

    @ManyToOne
    private ProgrammingLanguage programmingLanguage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Snippet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSnippet() {
        return snippet;
    }

    public Snippet snippet(String snippet) {
        this.snippet = snippet;
        return this;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getUrl() {
        return url;
    }

    public Snippet url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public Snippet durationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        return this;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Boolean isCommentsBlocked() {
        return commentsBlocked;
    }

    public Snippet commentsBlocked(Boolean commentsBlocked) {
        this.commentsBlocked = commentsBlocked;
        return this;
    }

    public void setCommentsBlocked(Boolean commentsBlocked) {
        this.commentsBlocked = commentsBlocked;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Snippet comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Snippet addComments(Comment comment) {
        this.comments.add(comment);
        comment.setSnippet(this);
        return this;
    }

    public Snippet removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setSnippet(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public Snippet ratings(Set<Rating> ratings) {
        this.ratings = ratings;
        return this;
    }

    public Snippet addRatings(Rating rating) {
        this.ratings.add(rating);
        rating.setSnippet(this);
        return this;
    }

    public Snippet removeRatings(Rating rating) {
        this.ratings.remove(rating);
        rating.setSnippet(null);
        return this;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public User getOwner() {
        return owner;
    }

    public Snippet owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public ProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
    }

    public Snippet programmingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
        return this;
    }

    public void setProgrammingLanguage(ProgrammingLanguage programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Snippet snippet = (Snippet) o;
        if (snippet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, snippet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Snippet{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", snippet='" + snippet + "'" +
            ", url='" + url + "'" +
            ", durationInMinutes='" + durationInMinutes + "'" +
            ", commentsBlocked='" + commentsBlocked + "'" +
            '}';
    }
}
