package com.lukaklacar.codesnip.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Snippet entity.
 */
public class SnippetDTO implements Serializable {

    private Long id;

    private String description;

    @NotNull
    private String snippet;

    private String url;

    @NotNull
    @Min(value = 1)
    private Integer durationInMinutes;

    private Boolean commentsBlocked;

    private Long ownerId;

    private Long programmingLanguageId;

    private String programmingLanguageName;

    public String getProgrammingLanguageName() {
        return programmingLanguageName;
    }

    public void setProgrammingLanguageName(String programmingLanguageName) {
        this.programmingLanguageName = programmingLanguageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Boolean getCommentsBlocked() {
        return commentsBlocked;
    }

    public void setCommentsBlocked(Boolean commentsBlocked) {
        this.commentsBlocked = commentsBlocked;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    public Long getProgrammingLanguageId() {
        return programmingLanguageId;
    }

    public void setProgrammingLanguageId(Long programmingLanguageId) {
        this.programmingLanguageId = programmingLanguageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SnippetDTO snippetDTO = (SnippetDTO) o;

        if (!Objects.equals(id, snippetDTO.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SnippetDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", snippet='" + snippet + "'" +
            ", url='" + url + "'" +
            ", durationInMinutes='" + durationInMinutes + "'" +
            ", commentsBlocked='" + commentsBlocked + "'" +
            '}';
    }
}
