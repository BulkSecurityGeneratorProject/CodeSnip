package com.lukaklacar.codesnip.service;

import com.lukaklacar.codesnip.service.dto.SnippetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Snippet.
 */
public interface SnippetService {

    /**
     * Save a snippet.
     *
     * @param snippetDTO the entity to save
     * @return the persisted entity
     */
    SnippetDTO save(SnippetDTO snippetDTO);

    /**
     *  Get all the snippets.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SnippetDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" snippet.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SnippetDTO findOne(Long id);

    /**
     *  Delete the "id" snippet.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
