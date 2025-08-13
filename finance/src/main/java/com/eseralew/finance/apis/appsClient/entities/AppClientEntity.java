package com.kefiya.home.apis.appsClient.entities;

import com.kefiya.home.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name="app_client")
@Getter
public class AppClientEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long Id;

    @Column(unique = true, nullable = false)
    String apiClientName;

    @Column(nullable = false)
    Integer clientId;

    @Column(unique = true, nullable = false)
    String apiKey;

    @Column(unique = true, nullable = false)
    String secret;

    @Column(unique = true, nullable = false)
    String hashKey;


    Boolean enabled;
}
