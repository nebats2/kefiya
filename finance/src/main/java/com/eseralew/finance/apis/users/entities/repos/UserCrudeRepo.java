package com.kefiya.home.apis.users.entities.repos;

import com.kefiya.home.apis.users.entities.entities.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCrudeRepo extends JpaRepository<UserLoginEntity,Long> {
     List<UserLoginEntity> findByMobile(String mobile);

     UserLoginEntity findByIdAndDeleted(Long id, Boolean deleted);
}
