package com.lukaklacar.codesnip.service.mapper;

import com.lukaklacar.codesnip.domain.*;
import com.lukaklacar.codesnip.service.dto.SnippetDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Snippet and its DTO SnippetDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface SnippetMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "programmingLanguage.id", target = "programmingLanguageId")
    SnippetDTO snippetToSnippetDTO(Snippet snippet);

    List<SnippetDTO> snippetsToSnippetDTOs(List<Snippet> snippets);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "programmingLanguageId", target = "programmingLanguage")
    Snippet snippetDTOToSnippet(SnippetDTO snippetDTO);

    List<Snippet> snippetDTOsToSnippets(List<SnippetDTO> snippetDTOs);

    default ProgrammingLanguage programmingLanguageFromId(Long id) {
        if (id == null) {
            return null;
        }
        ProgrammingLanguage programmingLanguage = new ProgrammingLanguage();
        programmingLanguage.setId(id);
        return programmingLanguage;
    }
}
