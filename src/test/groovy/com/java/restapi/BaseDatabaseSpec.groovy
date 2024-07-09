package com.java.restapi

import com.java.restapi.config.TestContainerConfiguration
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
class BaseDatabaseSpec extends Specification {

    @Autowired
    JdbcTemplate jdbcTemplate

    @Autowired
    EntityManager entityManager
}