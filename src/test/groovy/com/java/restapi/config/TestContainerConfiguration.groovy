package com.java.restapi.config

import com.java.restapi.RestApiApplication
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.JdbcTemplate
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.oracle.OracleContainer
import org.testcontainers.utility.MountableFile

import javax.sql.DataSource

@TestConfiguration(proxyBeanMethods = false)
class TestContainerConfiguration {

    @Bean
    @ServiceConnection
    OracleContainer oracleContainer() {
        String regex = ".*DATABASE IS READY TO USE!.*\\s"
        final OracleContainer oracleContainer = new OracleContainer("gvenzl/oracle-free:latest")
                .waitingFor(Wait.forLogMessage(regex, 1))
                .withUsername("REST_API")
                .withCopyToContainer(
                        MountableFile.forHostPath("docker-rest-api/oracle/"),
                        "/docker-entrypoint-initdb.d/"
                )

        return oracleContainer
    }

    @LiquibaseDataSource
    @Bean
    DataSource dataSource(@Autowired OracleContainer oracleContainer) {
        HikariDataSource dataSource = new HikariDataSource()
        dataSource.setJdbcUrl(oracleContainer.getJdbcUrl())
        dataSource.setUsername("REST_API_APP")
        dataSource.setPassword("pass")
        dataSource.setSchema("REST_API")

        return dataSource
    }

    @Bean
    JdbcTemplate getJdbcTemplate(@Autowired DataSource dataSource) {
        return new JdbcTemplate(dataSource)
    }

    static void main(String[] args) {
        SpringApplication.
                from(RestApiApplication::main)
                .with(TestContainerConfiguration.class)
                .run(args)
    }
}

