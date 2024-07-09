package com.java.restapi.controller

import com.java.restapi.BaseDatabaseSpec
import com.java.restapi.dto.RestApiResponseDTO
import com.java.restapi.errorhandling.handler.ResponseExceptionHandler
import com.java.restapi.mapper.RestApiResponseMapper
import com.java.restapi.model.RestApiEntity
import com.java.restapi.repository.RestApiRepository
import com.java.restapi.service.RestApiEntityServiceImpl
import com.java.restapi.service.RestApiServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate

class RestApiControllerSpec extends BaseDatabaseSpec {

    @Autowired
    TransactionTemplate transactionTemplate

    @Autowired
    RestApiResponseMapper responseMapper

    @Autowired
    RestApiServiceImpl restApiService

    @Autowired
    RestApiEntityServiceImpl restApiEntityService

    @Autowired
    RestApiRepository repository

    def "Should save login with count after performing 1 operation"() {
        given:
        final String testLogin = "octocat"

        when:
        RestApiResponseDTO responseDTO = restApiService.getInformationAboutUser(testLogin)

        then:
        noExceptionThrown()
        print responseDTO.login
        checkEquality(testLogin, responseDTO.login, 1)
    }

    def "Should save login with count after performing 2 operations"() {
        given:
        final String testLogin = "octocat"

        when:
        RestApiResponseDTO responseDTO = restApiService.getInformationAboutUser(testLogin)

        then:
        noExceptionThrown()
        print responseDTO.login
        checkEquality(testLogin, responseDTO.login, 2)
    }

    def "Should throw exception if something is wrong"() {
        given:
        final String testLogin = "testLogin12345"

        when:
        restApiService.getInformationAboutUser(testLogin)

        then:
        final Exception exception = thrown()
    }


    def checkEquality(login, loginFromResponse, numberOfRequests) {
        transactionTemplate.execute(status -> {
            RestApiEntity entity = repository.getReferenceById(loginFromResponse)
            print(entity)

            DataValidator.validateEquality(login, entity, numberOfRequests)
        })
        return true
    }
}

