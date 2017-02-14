package com.lukaklacar.codesnip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lukaklacar.codesnip.security.SecurityUtils;
import com.lukaklacar.codesnip.service.UserProfileService;
import com.lukaklacar.codesnip.web.rest.util.HeaderUtil;
import com.lukaklacar.codesnip.web.rest.util.PaginationUtil;
import com.lukaklacar.codesnip.service.dto.UserProfileDTO;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing UserProfile.
 */
@RestController
@RequestMapping("/api")
public class UserProfileResource {

    private final Logger log = LoggerFactory.getLogger(UserProfileResource.class);

    private static final String ENTITY_NAME = "userProfile";

    private final UserProfileService userProfileService;

    public UserProfileResource(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    /**
     * POST  /user-profiles : Create a new userProfile.
     *
     * @param userProfileDTO the userProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userProfileDTO, or with status 400 (Bad Request) if the userProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-profiles")
    @Timed
    public ResponseEntity<UserProfileDTO> createUserProfile(@RequestBody UserProfileDTO userProfileDTO) throws URISyntaxException {
        log.debug("REST request to save UserProfile : {}", userProfileDTO);
        if (userProfileDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userProfile cannot already have an ID")).body(null);
        }
        userProfileDTO.setUserId(SecurityUtils.getCurrentUserId());
        UserProfileDTO result = userProfileService.save(userProfileDTO);
        return ResponseEntity.created(new URI("/api/user-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-profiles : Updates an existing userProfile.
     *
     * @param userProfileDTO the userProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userProfileDTO,
     * or with status 400 (Bad Request) if the userProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the userProfileDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-profiles")
    @Timed
    public ResponseEntity<UserProfileDTO> updateUserProfile(@RequestBody UserProfileDTO userProfileDTO) throws URISyntaxException {
        log.debug("REST request to update UserProfile : {}", userProfileDTO);
        if (userProfileDTO.getId() == null) {
            return createUserProfile(userProfileDTO);
        }
        UserProfileDTO result = userProfileService.save(userProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-profiles : get all the userProfiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userProfiles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-profiles")
    @Timed
    public ResponseEntity<List<UserProfileDTO>> getAllUserProfiles(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserProfiles");
        Page<UserProfileDTO> page = userProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-profiles/:id : get the "id" userProfile.
     *
     * @param id the id of the userProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-profiles/{id}")
    @Timed
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Long id) {
        log.debug("REST request to get UserProfile : {}", id);
        UserProfileDTO userProfileDTO = userProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userProfileDTO));
    }

    /**
     * DELETE  /user-profiles/:id : delete the "id" userProfile.
     *
     * @param id the id of the userProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        log.debug("REST request to delete UserProfile : {}", id);
        userProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
