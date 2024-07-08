package com.java.restapi.repository;

import com.java.restapi.model.RestApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestApiRepository extends JpaRepository<RestApiEntity, String> {
}
