package com.kefiya.home.apis.users.entities.entities;

import com.kefiya.home.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name ="firebasetoken")
@Data
public class FirebaseTokenEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long Id;

    @NotNull
    String token;

    @ManyToOne
    UserLoginEntity user;

    String source;
}
