package com.java.restapi.errorhandling.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralServiceException extends RuntimeException {

    private final String errorMessage;
    private String errorCode;
    private int requestHashCode;

    public GeneralServiceException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public GeneralServiceException(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public GeneralServiceException(String errorMessage, String errorCode, int requestHashCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.requestHashCode = requestHashCode;
    }
}
