package com.java.restapi.model;

import com.java.restapi.common.TableName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = TableName.REQUESTS_COUNT_WITH_LOGIN)
@Getter
@Setter
@ToString
@SuperBuilder
@RequiredArgsConstructor
public class RestApiEntity implements Serializable {

    @Id
    @Column(name = "LOGIN")
    private String login;

    @Column(name = "REQUEST_COUNT")
    private Integer requestCount;
}
