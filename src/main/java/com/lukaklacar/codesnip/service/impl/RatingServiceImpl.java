package com.lukaklacar.codesnip.service.impl;

import com.lukaklacar.codesnip.service.RatingService;
import com.lukaklacar.codesnip.domain.Rating;
import com.lukaklacar.codesnip.repository.RatingRepository;
import com.lukaklacar.codesnip.service.dto.RatingDTO;
import com.lukaklacar.codesnip.service.mapper.RatingMapper;
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
 * Service Implementation for managing Rating.
 */
@Service
@Transactional
public class RatingServiceImpl implements RatingService{

    private final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);
    
    private final RatingRepository ratingRepository;

    private final RatingMapper ratingMapper;

    public RatingServiceImpl(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    /**
     * Save a rating.
     *
     * @param ratingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RatingDTO save(RatingDTO ratingDTO) {
        log.debug("Request to save Rating : {}", ratingDTO);
        Rating rating = ratingMapper.ratingDTOToRating(ratingDTO);
        rating = ratingRepository.save(rating);
        RatingDTO result = ratingMapper.ratingToRatingDTO(rating);
        return result;
    }

    /**
     *  Get all the ratings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RatingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ratings");
        Page<Rating> result = ratingRepository.findAll(pageable);
        return result.map(rating -> ratingMapper.ratingToRatingDTO(rating));
    }

    /**
     *  Get one rating by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RatingDTO findOne(Long id) {
        log.debug("Request to get Rating : {}", id);
        Rating rating = ratingRepository.findOne(id);
        RatingDTO ratingDTO = ratingMapper.ratingToRatingDTO(rating);
        return ratingDTO;
    }

    /**
     *  Delete the  rating by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rating : {}", id);
        ratingRepository.delete(id);
    }
}
