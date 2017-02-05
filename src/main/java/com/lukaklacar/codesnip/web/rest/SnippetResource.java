package com.lukaklacar.codesnip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lukaklacar.codesnip.service.SnippetService;
import com.lukaklacar.codesnip.web.rest.util.HeaderUtil;
import com.lukaklacar.codesnip.web.rest.util.PaginationUtil;
import com.lukaklacar.codesnip.service.dto.SnippetDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Snippet.
 */
@RestController
@RequestMapping("/api")
public class SnippetResource {

    private final Logger log = LoggerFactory.getLogger(SnippetResource.class);

    private static final String ENTITY_NAME = "snippet";

    private final SnippetService snippetService;

    public SnippetResource(SnippetService snippetService) {
        this.snippetService = snippetService;
    }

    /**
     * POST  /snippets : Create a new snippet.
     *
     * @param snippetDTO the snippetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new snippetDTO, or with status 400 (Bad Request) if the snippet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/snippets")
    @Timed
    public ResponseEntity<SnippetDTO> createSnippet(@Valid @RequestBody SnippetDTO snippetDTO) throws URISyntaxException {
            log.debug("REST request to save Snippet : {}", snippetDTO);
        if (snippetDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new snippet cannot already have an ID")).body(null);
        }
        SnippetDTO result = snippetService.save(snippetDTO);
        return ResponseEntity.created(new URI("/api/snippets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /snippets : Updates an existing snippet.
     *
     * @param snippetDTO the snippetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated snippetDTO,
     * or with status 400 (Bad Request) if the snippetDTO is not valid,
     * or with status 500 (Internal Server Error) if the snippetDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/snippets")
    @Timed
    public ResponseEntity<SnippetDTO> updateSnippet(@Valid @RequestBody SnippetDTO snippetDTO) throws URISyntaxException {
        log.debug("REST request to update Snippet : {}", snippetDTO);
        if (snippetDTO.getId() == null) {
            return createSnippet(snippetDTO);
        }
        SnippetDTO result = snippetService.save(snippetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, snippetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /snippets : get all the snippets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of snippets in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/snippets")
    @Timed
    public ResponseEntity<List<SnippetDTO>> getAllSnippets(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Snippets");
        Page<SnippetDTO> page = snippetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/snippets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /snippets/:id : get the "id" snippet.
     *
     * @param id the id of the snippetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the snippetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/snippets/{id}")
    @Timed
    public ResponseEntity<SnippetDTO> getSnippet(@PathVariable Long id) {
        log.debug("REST request to get Snippet : {}", id);
        SnippetDTO snippetDTO = snippetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(snippetDTO));
    }

    /**
     * DELETE  /snippets/:id : delete the "id" snippet.
     *
     * @param id the id of the snippetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/snippets/{id}")
    @Timed
    public ResponseEntity<Void> deleteSnippet(@PathVariable Long id) {
        log.debug("REST request to delete Snippet : {}", id);
        snippetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
