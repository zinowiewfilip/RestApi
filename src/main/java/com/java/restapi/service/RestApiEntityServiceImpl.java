package com.java.restapi.service;

import com.java.restapi.model.RestApiEntity;
import com.java.restapi.repository.RestApiRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RestApiEntityServiceImpl implements RestApiEntityService {

    private final RestApiRepository restApiRepository;

    @Override
    @Transactional
    public void saveOrUpdateLoginAndRequestCount(final String login) {
        RestApiEntity entity;
        try {
            entity = this.restApiRepository.findById(login)
                    .orElseThrow();
            entity.setRequestCount(entity.getRequestCount() + 1);
        } catch (NoSuchElementException e) {
            entity = RestApiEntity.builder()
                            .requestCount(1)
                            .login(login)
                            .build();
        }
        this.restApiRepository.save(entity);
    }
}
