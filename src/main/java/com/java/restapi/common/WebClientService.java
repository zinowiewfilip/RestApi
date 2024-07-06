package com.java.restapi.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.restapi.errorhandling.dto.ServiceResponse;
import com.java.restapi.errorhandling.exceptions.GeneralServiceException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class WebClientService {

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

    private static final Duration MINIMUM_REQUEST_TIMEOUT = Duration.ofSeconds(2);

    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper mapper;

    public <R> R getObject(String url, Class<R> returnType) {
        return this.webClientBuilder.baseUrl(url).build()
                .get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .bodyToMono(returnType)
                .block();
    }

    private Mono<Throwable> handleError(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class).flatMap(body -> {
            try {
                return Mono.error(new GeneralServiceException(
                        this.mapper.readValue(body.getBytes(), ServiceResponse.class).getErrorCode(),
                        this.mapper.readValue(body.getBytes(), ServiceResponse.class).getErrorMessage()));
            } catch (Exception e) {
                return Mono.error(new RuntimeException("There is a problem with processing your request"));
            }
        });
    }
}
