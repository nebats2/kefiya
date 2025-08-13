package com.kefiya.home.common;

import com.kefiya.home.apis.staffApi.services.StaffAuthenticateService;
import com.kefiya.home.services.controllers.ContextService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
@Slf4j
public class BaseValidator {

    private final ContextService contextService;
    private  final StaffAuthenticateService staffAuthenticateService;


    protected void validatePhotoRoles(Long profileId) throws BaseException {
        if(contextService.isAdmin()){
            staffAuthenticateService.authorizeProfileRole();
        }
        else if(contextService.isProfile()){
            return;
        } else{
            log.error("unknown context or api");
            throw new BaseException(ErrorMessage.user_has_no_permission);
        }
    }

  public  void validateObjectsNotNull(Object...objects) throws BaseException {
        if( objects.length <1 ) return;

        if(Arrays.stream(objects).anyMatch(Objects::isNull)){
            throw new BaseException(ErrorMessage.entity_is_not_found);
        }
  }
    public String validateNameFormat(String name) {
        String var10000 = name.substring(0, 1).toUpperCase();
        return var10000 + name.substring(1).toLowerCase();
    }

    public void validateEntityStatus(EntityStatusEnum status, String entityName) throws BaseException {
        if (null == status) {
            throw new BaseException(ErrorMessage.entity_is_not_active);
        } else if (status.equals(EntityStatusEnum.DELETED)) {
            throw new BaseException(ErrorMessage.entity_is_deleted);
        } else if (status.equals(EntityStatusEnum.BLOCKED)) {
            throw new BaseException(ErrorMessage.entity_is_blocked);
        } else if (status.equals(EntityStatusEnum.SUSPENDED)) {
            throw new BaseException(ErrorMessage.entity_is_suspended);
        }
    }

    public void validatePassword(String password) throws BaseException {
        String regex = "^(?=.*[a-zA-Z]).{6,50}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()) {
            throw new BaseException(ErrorMessage.name_is_not_valid);
        }
    }

    public void validatePhone(String phone) throws BaseException {
        String regex = "^[0-9]{10,15}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        if(!matcher.matches()){

        throw new BaseException(ErrorMessage.invalid_mobile);

        }
    }

    public void validateEmail(String email) throws BaseException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) {
            throw new BaseException(ErrorMessage.name_is_not_valid);
        }
    }

    public String validateFullName(String name) throws BaseException {
        if (!name.isBlank() && !name.isEmpty()) {
            if (!name.contains(" ")) {
                throw new BaseException(ErrorMessage.name_is_not_valid);
            } else {
                String[] names = name.split(" ");
                if (names.length >= 2 && names.length <= 3) {
                    String formattedName = "";

                    for(String n : names) {
                        formattedName = formattedName + n.substring(0, 1).toUpperCase() + n.substring(1).toLowerCase() + " ";
                    }

                    return formattedName;
                } else {
                    throw new BaseException(ErrorMessage.name_is_not_valid);
                }
            }
        } else {
            throw new BaseException(ErrorMessage.name_is_not_valid);
        }
    }
}
