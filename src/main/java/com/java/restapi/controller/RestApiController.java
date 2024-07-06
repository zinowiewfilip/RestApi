package com.java.restapi.controller;

import com.java.restapi.service.RestApiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/occupancy")
@RequiredArgsConstructor
@Tag(name = "RestApi", description = "Endpoints for our rest service")
public class RestApiController {

    private final RestApiService restApiService;
}
