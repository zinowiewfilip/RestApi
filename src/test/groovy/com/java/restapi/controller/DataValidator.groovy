package com.java.restapi.controller

import com.java.restapi.model.RestApiEntity

class DataValidator {

    def static validateEquality(String login, RestApiEntity entity, Integer numberOfRequests) {

        assert entity.login == login
        assert entity.requestCount == numberOfRequests

    }
}

