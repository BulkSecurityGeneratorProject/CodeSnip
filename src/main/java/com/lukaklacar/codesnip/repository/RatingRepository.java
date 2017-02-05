package com.lukaklacar.codesnip.repository;

import com.lukaklacar.codesnip.domain.Rating;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Rating entity.
 */
@SuppressWarnings("unused")
public interface RatingRepository extends JpaRepository<Rating,Long> {

    @Query("select rating from Rating rating where rating.owner.login = ?#{principal.username}")
    List<Rating> findByOwnerIsCurrentUser();

}
