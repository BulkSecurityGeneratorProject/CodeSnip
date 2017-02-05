package com.lukaklacar.codesnip.service.impl;

import com.lukaklacar.codesnip.service.ProgrammingLanguageService;
import com.lukaklacar.codesnip.domain.ProgrammingLanguage;
import com.lukaklacar.codesnip.repository.ProgrammingLanguageRepository;
import com.lukaklacar.codesnip.service.dto.ProgrammingLanguageDTO;
import com.lukaklacar.codesnip.service.mapper.ProgrammingLanguageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProgrammingLanguage.
 */
@Service
@Transactional
public class ProgrammingLanguageServiceImpl implements ProgrammingLanguageService{

    private final Logger log = LoggerFactory.getLogger(ProgrammingLanguageServiceImpl.class);
    
    private final ProgrammingLanguageRepository programmingLanguageRepository;

    private final ProgrammingLanguageMapper programmingLanguageMapper;

    public ProgrammingLanguageServiceImpl(ProgrammingLanguageRepository programmingLanguageRepository, ProgrammingLanguageMapper programmingLanguageMapper) {
        this.programmingLanguageRepository = programmingLanguageRepository;
        this.programmingLanguageMapper = programmingLanguageMapper;
    }

    /**
     * Save a programmingLanguage.
     *
     * @param programmingLanguageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProgrammingLanguageDTO save(ProgrammingLanguageDTO programmingLanguageDTO) {
        log.debug("Request to save ProgrammingLanguage : {}", programmingLanguageDTO);
        ProgrammingLanguage programmingLanguage = programmingLanguageMapper.programmingLanguageDTOToProgrammingLanguage(programmingLanguageDTO);
        programmingLanguage = programmingLanguageRepository.save(programmingLanguage);
        ProgrammingLanguageDTO result = programmingLanguageMapper.programmingLanguageToProgrammingLanguageDTO(programmingLanguage);
        return result;
    }

    /**
     *  Get all the programmingLanguages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProgrammingLanguageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProgrammingLanguages");
        Page<ProgrammingLanguage> result = programmingLanguageRepository.findAll(pageable);
        return result.map(programmingLanguage -> programmingLanguageMapper.programmingLanguageToProgrammingLanguageDTO(programmingLanguage));
    }

    /**
     *  Get one programmingLanguage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProgrammingLanguageDTO findOne(Long id) {
        log.debug("Request to get ProgrammingLanguage : {}", id);
        ProgrammingLanguage programmingLanguage = programmingLanguageRepository.findOne(id);
        ProgrammingLanguageDTO programmingLanguageDTO = programmingLanguageMapper.programmingLanguageToProgrammingLanguageDTO(programmingLanguage);
        return programmingLanguageDTO;
    }

    /**
     *  Delete the  programmingLanguage by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProgrammingLanguage : {}", id);
        programmingLanguageRepository.delete(id);
    }
}
