package com.java.restapi.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.restapi.dto.GitHubUsersApiResponseDTO;
import com.java.restapi.errorhandling.dto.ServiceResponse;
import com.java.restapi.errorhandling.exception.GeneralServiceException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class WebClientServiceTest {

    private MockWebServer mockWebServer;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private ServiceResponse serviceResponse;

    @InjectMocks
    private WebClientService webClientService;

    @BeforeEach
    void setUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        webClientService = new WebClientService(WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build(), mapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void testGetObjectSuccess() {
        String expectedResponse = "{\"name\":\"octocat\"}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(expectedResponse)
                .addHeader("Content-Type", "application/json")
                .setResponseCode(HttpStatus.OK.value()));

        String url = "/users/octocat";
        GitHubUsersApiResponseDTO response = webClientService.getObject(url, GitHubUsersApiResponseDTO.class);

        assertEquals("octocat", response.getName());
    }

    @Test
    void testGetObjectError() throws Exception {
        String errorResponse = "{\"errorCode\":\"404\",\"errorMessage\":\"Not Found\"}";

        mockWebServer.enqueue(new MockResponse()
                .setBody(errorResponse)
                .addHeader("Content-Type", "application/json")
                .setResponseCode(HttpStatus.NOT_FOUND.value()));

        doReturn(String.valueOf(HttpStatus.NOT_FOUND.value())).when(serviceResponse).getErrorCode();
        doReturn(HttpStatus.NOT_FOUND.toString()).when(serviceResponse).getErrorMessage();
        doReturn(serviceResponse).when(mapper).readValue(any(byte[].class), eq(ServiceResponse.class));

        String url = "/users/octocat";

        GeneralServiceException exception = assertThrows(GeneralServiceException.class, () -> webClientService.getObject(url, GitHubUsersApiResponseDTO.class));

        assertAll(
                () -> assertEquals(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getErrorCode()),
                () -> assertEquals(HttpStatus.NOT_FOUND.toString(), exception.getErrorMessage())
        );
    }
}

