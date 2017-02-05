package com.lukaklacar.codesnip.service.mapper;

import com.lukaklacar.codesnip.domain.*;
import com.lukaklacar.codesnip.service.dto.ProgrammingLanguageDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity ProgrammingLanguage and its DTO ProgrammingLanguageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProgrammingLanguageMapper {

    ProgrammingLanguageDTO programmingLanguageToProgrammingLanguageDTO(ProgrammingLanguage programmingLanguage);

    List<ProgrammingLanguageDTO> programmingLanguagesToProgrammingLanguageDTOs(List<ProgrammingLanguage> programmingLanguages);

    @Mapping(target = "snippets", ignore = true)
    ProgrammingLanguage programmingLanguageDTOToProgrammingLanguage(ProgrammingLanguageDTO programmingLanguageDTO);

    List<ProgrammingLanguage> programmingLanguageDTOsToProgrammingLanguages(List<ProgrammingLanguageDTO> programmingLanguageDTOs);
}
