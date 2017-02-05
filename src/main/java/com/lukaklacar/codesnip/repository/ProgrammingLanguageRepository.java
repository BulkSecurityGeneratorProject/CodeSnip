package com.lukaklacar.codesnip.repository;

import com.lukaklacar.codesnip.domain.ProgrammingLanguage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProgrammingLanguage entity.
 */
@SuppressWarnings("unused")
public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguage,Long> {

}
