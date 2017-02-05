package com.lukaklacar.codesnip.repository;

import com.lukaklacar.codesnip.domain.UserProfile;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserProfile entity.
 */
@SuppressWarnings("unused")
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {

}
