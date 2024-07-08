package com.java.restapi.service;

import com.java.restapi.common.WebClientService;
import com.java.restapi.dto.GitHubUsersApiResponseDTO;
import com.java.restapi.dto.RestApiResponseDTO;
import com.java.restapi.mapper.RestApiResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestApiServiceImpl implements RestApiService {

    @Value("${api.url}")
    private String API_URL;

    private final WebClientService webClientService;

    private final RestApiResponseMapper restApiResponseMapper;

    private final RestApiEntityService restApiEntityService;

    @Override
    public RestApiResponseDTO getInformationAboutUser(final String login) {
        this.restApiEntityService.saveOrUpdateLoginAndRequestCount(login);
        return this.restApiResponseMapper
                .toRestApiResponseDTO(
                        this.webClientService.getObject(
                                API_URL + login, GitHubUsersApiResponseDTO.class));
    }
}
