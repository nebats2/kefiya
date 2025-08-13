package com.kefiya.home.apis.users.views;

import com.kefiya.home.apis.users.entities.entities.UserLoginEntity;
import lombok.Data;

@Data
public class ResponseUser {
    Long id;
    String firstName;
    String lastName;
    String middleName;
    String mobile;
    String email;
    boolean mobileConfirmed;
    boolean emailConfirmed;
    String sex;
    String dob;

    String loginStatus;
    String language;

    String firstLoginTime;
    String lastLoginTime;

    public static ResponseUser build(UserLoginEntity entity){
        var response = new ResponseUser();
        response.setId(entity.getId());
        response.setFirstName(entity.getFirstName());
        response.setMiddleName(entity.getMiddleName());
        response.setLastName(entity.getLastName());
        response.setMobile(entity.getMobile());
        response.setEmail(entity.getEmail());
        response.setMobileConfirmed(entity.getMobileConfirmed());
        response.setEmailConfirmed(entity.getEmailConfirmed());
        response.setDob(entity.getDob());
        response.setSex(entity.getSex());
        response.setLanguage(entity.getLanguage());
        response.setFirstLoginTime(entity.getFirstLogin() !=null ? entity.getFirstLogin().toString() : null);

        response.setLoginStatus(entity.getLoginStatus().name());

        response.setLastLoginTime(entity.getLastLogin()!=null ? entity.getLastLogin().toString() : null);
        return response;
    }
}
