package com.lukaklacar.codesnip.repository;

import com.lukaklacar.codesnip.domain.Snippet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Snippet entity.
 */
@SuppressWarnings("unused")
public interface SnippetRepository extends JpaRepository<Snippet,Long> {

    @Query("select snippet from Snippet snippet where snippet.owner.login = ?#{principal.username}")
    List<Snippet> findByOwnerIsCurrentUser();

}
