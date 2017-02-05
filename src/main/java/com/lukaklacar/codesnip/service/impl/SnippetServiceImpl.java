package com.lukaklacar.codesnip.service.impl;

import com.lukaklacar.codesnip.service.SnippetService;
import com.lukaklacar.codesnip.domain.Snippet;
import com.lukaklacar.codesnip.repository.SnippetRepository;
import com.lukaklacar.codesnip.service.dto.SnippetDTO;
import com.lukaklacar.codesnip.service.mapper.SnippetMapper;
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
 * Service Implementation for managing Snippet.
 */
@Service
@Transactional
public class SnippetServiceImpl implements SnippetService{

    private final Logger log = LoggerFactory.getLogger(SnippetServiceImpl.class);

    private final SnippetRepository snippetRepository;

    private final SnippetMapper snippetMapper;

    public SnippetServiceImpl(SnippetRepository snippetRepository, SnippetMapper snippetMapper) {
        this.snippetRepository = snippetRepository;
        this.snippetMapper = snippetMapper;
    }

    /**
     * Save a snippet.
     *
     * @param snippetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SnippetDTO save(SnippetDTO snippetDTO) {
        log.debug("Request to save Snippet : {}", snippetDTO);
        Snippet snippet = snippetMapper.snippetDTOToSnippet(snippetDTO);
        snippet = snippetRepository.save(snippet);
        SnippetDTO result = snippetMapper.snippetToSnippetDTO(snippet);
        return result;
    }

    /**
     *  Get all the snippets.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SnippetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Snippets");
        Page<Snippet> result = snippetRepository.findAll(pageable);
        return result.map(snippet -> snippetMapper.snippetToSnippetDTO(snippet));
    }

    /**
     *  Get one snippet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SnippetDTO findOne(Long id) {
        log.debug("Request to get Snippet : {}", id);
        Snippet snippet = snippetRepository.findOne(id);
        SnippetDTO snippetDTO = snippetMapper.snippetToSnippetDTO(snippet);
        return snippetDTO;
    }

    /**
     *  Delete the  snippet by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Snippet : {}", id);
        snippetRepository.delete(id);
    }
}
