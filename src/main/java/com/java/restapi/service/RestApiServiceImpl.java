package com.java.restapi.service;

import com.java.restapi.common.UrlCommon;
import com.java.restapi.common.WebClientService;
import com.java.restapi.dto.GitHubUsersApiResponseDTO;
import com.java.restapi.dto.RestApiResponseDTO;
import com.java.restapi.mapper.RestApiResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestApiServiceImpl implements RestApiService {

    private final WebClientService webClientService;

    private final RestApiResponseMapper restApiResponseMapper;

    private final RestApiEntityService restApiEntityService;

    @Override
    public RestApiResponseDTO getInformationAboutUser(final String login) {
        this.restApiEntityService.saveOrUpdateLoginAndRequestCount(login);
        return this.restApiResponseMapper
                .toRestApiResponseDTO(
                        this.webClientService.getObject(
                                UrlCommon.API_URL + login, GitHubUsersApiResponseDTO.class));
    }
}
