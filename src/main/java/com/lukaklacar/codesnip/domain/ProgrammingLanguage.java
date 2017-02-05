package com.lukaklacar.codesnip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProgrammingLanguage.
 */
@Entity
@Table(name = "programming_language")
public class ProgrammingLanguage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "programmingLanguage")
    @JsonIgnore
    private Set<Snippet> snippets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProgrammingLanguage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Snippet> getSnippets() {
        return snippets;
    }

    public ProgrammingLanguage snippets(Set<Snippet> snippets) {
        this.snippets = snippets;
        return this;
    }

    public ProgrammingLanguage addSnippets(Snippet snippet) {
        this.snippets.add(snippet);
        snippet.setProgrammingLanguage(this);
        return this;
    }

    public ProgrammingLanguage removeSnippets(Snippet snippet) {
        this.snippets.remove(snippet);
        snippet.setProgrammingLanguage(null);
        return this;
    }

    public void setSnippets(Set<Snippet> snippets) {
        this.snippets = snippets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProgrammingLanguage programmingLanguage = (ProgrammingLanguage) o;
        if (programmingLanguage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, programmingLanguage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProgrammingLanguage{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
