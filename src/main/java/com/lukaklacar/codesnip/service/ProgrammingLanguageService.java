package com.lukaklacar.codesnip.service;

import com.lukaklacar.codesnip.service.dto.ProgrammingLanguageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ProgrammingLanguage.
 */
public interface ProgrammingLanguageService {

    /**
     * Save a programmingLanguage.
     *
     * @param programmingLanguageDTO the entity to save
     * @return the persisted entity
     */
    ProgrammingLanguageDTO save(ProgrammingLanguageDTO programmingLanguageDTO);

    /**
     *  Get all the programmingLanguages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProgrammingLanguageDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" programmingLanguage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProgrammingLanguageDTO findOne(Long id);

    /**
     *  Delete the "id" programmingLanguage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
