package com.java.restapi.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.restapi.dto.GitHubUsersApiResponseDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class WebClientServiceTest {

    @Mock
    private GitHubUsersApiResponseDTO gitHubUsersApiResponseDTO;

    @Autowired
    private WebClient webClient;

    @MockBean
    private WebClient.Builder webClientBuilder;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private WebClientService webClientService;

    public static MockWebServer mockWebServer;

    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        doReturn(webClientBuilder).when(webClientBuilder).baseUrl(mockWebServer.url("/").toString());
        doReturn(WebClient.builder().baseUrl(mockWebServer.url("/").toString()).build()).when(webClientBuilder).build();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testIfWebClientIsReturningCorrectData() {
        String mockResponseBody = "{\"type\":\"User\"}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json")
                .setResponseCode(HttpStatus.OK.value()));

        Mono<GitHubUsersApiResponseDTO> responseMono = Mono.just(webClientService.getObject("/test", GitHubUsersApiResponseDTO.class)); //didn't have time to check it correctly


        // Verify the result
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> "type".equals(response.getType()))
                .verifyComplete();

    }
}

