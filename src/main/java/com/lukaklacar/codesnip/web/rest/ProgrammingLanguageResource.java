package com.lukaklacar.codesnip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lukaklacar.codesnip.service.ProgrammingLanguageService;
import com.lukaklacar.codesnip.web.rest.util.HeaderUtil;
import com.lukaklacar.codesnip.web.rest.util.PaginationUtil;
import com.lukaklacar.codesnip.service.dto.ProgrammingLanguageDTO;
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
 * REST controller for managing ProgrammingLanguage.
 */
@RestController
@RequestMapping("/api")
public class ProgrammingLanguageResource {

    private final Logger log = LoggerFactory.getLogger(ProgrammingLanguageResource.class);

    private static final String ENTITY_NAME = "programmingLanguage";
        
    private final ProgrammingLanguageService programmingLanguageService;

    public ProgrammingLanguageResource(ProgrammingLanguageService programmingLanguageService) {
        this.programmingLanguageService = programmingLanguageService;
    }

    /**
     * POST  /programming-languages : Create a new programmingLanguage.
     *
     * @param programmingLanguageDTO the programmingLanguageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new programmingLanguageDTO, or with status 400 (Bad Request) if the programmingLanguage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/programming-languages")
    @Timed
    public ResponseEntity<ProgrammingLanguageDTO> createProgrammingLanguage(@Valid @RequestBody ProgrammingLanguageDTO programmingLanguageDTO) throws URISyntaxException {
        log.debug("REST request to save ProgrammingLanguage : {}", programmingLanguageDTO);
        if (programmingLanguageDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new programmingLanguage cannot already have an ID")).body(null);
        }
        ProgrammingLanguageDTO result = programmingLanguageService.save(programmingLanguageDTO);
        return ResponseEntity.created(new URI("/api/programming-languages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /programming-languages : Updates an existing programmingLanguage.
     *
     * @param programmingLanguageDTO the programmingLanguageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated programmingLanguageDTO,
     * or with status 400 (Bad Request) if the programmingLanguageDTO is not valid,
     * or with status 500 (Internal Server Error) if the programmingLanguageDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/programming-languages")
    @Timed
    public ResponseEntity<ProgrammingLanguageDTO> updateProgrammingLanguage(@Valid @RequestBody ProgrammingLanguageDTO programmingLanguageDTO) throws URISyntaxException {
        log.debug("REST request to update ProgrammingLanguage : {}", programmingLanguageDTO);
        if (programmingLanguageDTO.getId() == null) {
            return createProgrammingLanguage(programmingLanguageDTO);
        }
        ProgrammingLanguageDTO result = programmingLanguageService.save(programmingLanguageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, programmingLanguageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /programming-languages : get all the programmingLanguages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of programmingLanguages in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/programming-languages")
    @Timed
    public ResponseEntity<List<ProgrammingLanguageDTO>> getAllProgrammingLanguages(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProgrammingLanguages");
        Page<ProgrammingLanguageDTO> page = programmingLanguageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/programming-languages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /programming-languages/:id : get the "id" programmingLanguage.
     *
     * @param id the id of the programmingLanguageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the programmingLanguageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/programming-languages/{id}")
    @Timed
    public ResponseEntity<ProgrammingLanguageDTO> getProgrammingLanguage(@PathVariable Long id) {
        log.debug("REST request to get ProgrammingLanguage : {}", id);
        ProgrammingLanguageDTO programmingLanguageDTO = programmingLanguageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(programmingLanguageDTO));
    }

    /**
     * DELETE  /programming-languages/:id : delete the "id" programmingLanguage.
     *
     * @param id the id of the programmingLanguageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/programming-languages/{id}")
    @Timed
    public ResponseEntity<Void> deleteProgrammingLanguage(@PathVariable Long id) {
        log.debug("REST request to delete ProgrammingLanguage : {}", id);
        programmingLanguageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
