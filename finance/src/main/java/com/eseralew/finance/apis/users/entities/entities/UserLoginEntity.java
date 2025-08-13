package com.kefiya.home.apis.users.entities.entities;

import com.kefiya.home.apis.users.enums.UserLoginStatusEnum;
import com.kefiya.home.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name ="user_login")
@Data
public class UserLoginEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long Id;


    @Column(unique=true)
    String mobile;

    String email;

    @Column(nullable = false)
    String password;
    String oldPassword;

    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String middleName;

    String lastName;
    String sex;
    String dob;
    LocalDateTime firstLogin;
    LocalDateTime lastLogin;

    @Column(nullable = false)
    Boolean mobileConfirmed = false;

    @Column(nullable = false)
    Boolean emailConfirmed = false;

    String mobileConfirmCode;
    String emailConfirmCode;

    LocalDateTime mobileCodeSentDateTime;
    LocalDateTime emailCodeSentDateTime;
    String uuid;


    String lastLocation;
    LocalDateTime lastLocationTime;

    String language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserLoginStatusEnum loginStatus;

}
