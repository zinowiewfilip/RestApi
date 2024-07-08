package com.java.restapi.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RestApiResponseDTO {
    private String id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private String createdAt;
    private String calculations;
}
