package com.lukaklacar.codesnip.service.mapper;

import com.lukaklacar.codesnip.domain.*;
import com.lukaklacar.codesnip.service.dto.RatingDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Rating and its DTO RatingDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface RatingMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "snippet.id", target = "snippetId")
    RatingDTO ratingToRatingDTO(Rating rating);

    List<RatingDTO> ratingsToRatingDTOs(List<Rating> ratings);

    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "snippetId", target = "snippet")
    Rating ratingDTOToRating(RatingDTO ratingDTO);

    List<Rating> ratingDTOsToRatings(List<RatingDTO> ratingDTOs);

    default Snippet snippetFromId(Long id) {
        if (id == null) {
            return null;
        }
        Snippet snippet = new Snippet();
        snippet.setId(id);
        return snippet;
    }
}
