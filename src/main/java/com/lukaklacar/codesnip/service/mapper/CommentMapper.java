package com.lukaklacar.codesnip.service.mapper;

import com.lukaklacar.codesnip.domain.*;
import com.lukaklacar.codesnip.service.dto.CommentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface CommentMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "snippet.id", target = "snippetId")
    CommentDTO commentToCommentDTO(Comment comment);

    List<CommentDTO> commentsToCommentDTOs(List<Comment> comments);

    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "snippetId", target = "snippet")
    Comment commentDTOToComment(CommentDTO commentDTO);

    List<Comment> commentDTOsToComments(List<CommentDTO> commentDTOs);

    default Snippet snippetFromId(Long id) {
        if (id == null) {
            return null;
        }
        Snippet snippet = new Snippet();
        snippet.setId(id);
        return snippet;
    }
}
