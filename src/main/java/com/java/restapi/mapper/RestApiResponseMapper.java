package com.java.restapi.mapper;

import com.java.restapi.dto.GitHubUsersApiResponseDTO;
import com.java.restapi.dto.RestApiResponseDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS)
public interface RestApiResponseMapper {

    @Mapping(target = "calculations", expression = "java(String.valueOf((6 / response.getFollowers()) * (2 + response.getPublicRepos())))")
    @Mapping(target = "createdAt", source = "createdAt")
    RestApiResponseDTO toRestApiResponseDTO(GitHubUsersApiResponseDTO response);
}
