package com.lukaklacar.codesnip.service.mapper;

import com.lukaklacar.codesnip.domain.*;
import com.lukaklacar.codesnip.service.dto.UserProfileDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UserProfile and its DTO UserProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface UserProfileMapper {

    @Mapping(source = "user.id", target = "userId")
    UserProfileDTO userProfileToUserProfileDTO(UserProfile userProfile);

    List<UserProfileDTO> userProfilesToUserProfileDTOs(List<UserProfile> userProfiles);

    @Mapping(source = "userId", target = "user")
    UserProfile userProfileDTOToUserProfile(UserProfileDTO userProfileDTO);

    List<UserProfile> userProfileDTOsToUserProfiles(List<UserProfileDTO> userProfileDTOs);
}
