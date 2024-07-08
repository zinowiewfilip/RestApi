package com.java.restapi.service;

import com.java.restapi.dto.RestApiResponseDTO;

public interface RestApiService {
    RestApiResponseDTO getInformationAboutUser(String login);
}
