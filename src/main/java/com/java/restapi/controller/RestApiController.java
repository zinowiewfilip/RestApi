package com.java.restapi.controller;

import com.java.restapi.dto.RestApiResponseDTO;
import com.java.restapi.errorhandling.handler.ResponseExceptionHandler;
import com.java.restapi.service.RestApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "RestApi", description = "Endpoints for our rest service")
public class RestApiController {

    private final RestApiService restApiService;

    @GetMapping("/{login}")
    @Operation(
            summary = "Getting information about user",
            description = "Getting information about user and performing operation after that",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RestApiResponseDTO.class))
                    ),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ResponseExceptionHandler.ExceptionResponse.class))
                            }
                    )
            }
    )
    public ResponseEntity<RestApiResponseDTO> getInformationAboutUser(@PathVariable String login) {
        return ResponseEntity.ok(
                this.restApiService.getInformationAboutUser(login));
    }
}
