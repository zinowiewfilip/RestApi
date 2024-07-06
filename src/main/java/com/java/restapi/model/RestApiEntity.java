package com.java.restapi.model;

import com.java.restapi.common.TableNames;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = TableNames.REQUESTS_COUNT_WITH_LOGIN)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class RestApiEntity implements Serializable {

    @Id
    @Column(name = "LOGIN")
    private String login;

    @Column(name = "REQUEST_COUNT")
    private Integer requestCount;
}
