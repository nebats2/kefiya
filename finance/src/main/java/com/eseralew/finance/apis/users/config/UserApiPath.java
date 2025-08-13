package com.kefiya.home.apis.users.config;

import com.kefiya.home.configs.AppConfigConstant;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class UserApiPath {
    private final AppConfigConstant appConfigConstant;

    public String updateProperty(){
        return appConfigConstant.getUserUrl()+"/update/property";
    }

    public String updatePassword(){
        return appConfigConstant.getUserUrl()+"/update/password";
    }

    public String delete(Long userId){
        return appConfigConstant.getUserUrl()+"/update/delete/"+userId;
    }

    public String deleteConfirm(Long userId, String code){
        return appConfigConstant.getUserUrl()+"/update/delete/confirm/"+userId +"/"+code;
    }
    public String updateFbToken( ){
        return appConfigConstant.getUserUrl()+"/update/fbToken/";
    }

    public String updateAdminPasswordChange(Long userId, String password){
        return appConfigConstant.getUserUrl()+"/update/admin/password/"+userId +"/"+password;
    }


    public String createUser(){
        return appConfigConstant.getUserUrl()+"/user/create";
    }

    public String confirmMobile(){
        return appConfigConstant.getUserUrl()+"/user/confirm/mobile";
    }

    public String resentConfirmationCode(Long userId){
        return appConfigConstant.getUserUrl()+"/user/confirm/mobile/resend/"+userId;
    }

    public String passwordRecoveryCreate(String mobile){
        return appConfigConstant.getUserUrl()+"/user/password/recovery/"+mobile;
    }

    public String passwordRecoveryReset(){
        return appConfigConstant.getUserUrl()+"/user/password/recovery/reset";
    }
}
