package com.kefiya.home.apis.users.entities;

import com.kefiya.home.apis.users.entities.entities.UserLoginEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserSearchSpecification {

    public static Specification<UserLoginEntity> notDeleted(){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("deleted"), false));
    }
    public static Specification<UserLoginEntity> mobile(String mobile){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("mobile"), "%"+mobile+"%"));
    }
    public static Specification<UserLoginEntity> firstName(String name){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%"+name+"%"));
    }
    public static Specification<UserLoginEntity> middleName(String name){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("middleName"), "%"+name+"%"));
    }
}
