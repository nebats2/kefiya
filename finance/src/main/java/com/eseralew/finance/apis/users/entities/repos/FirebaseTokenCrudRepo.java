package com.kefiya.home.apis.users.entities.repos;

import com.kefiya.home.apis.users.entities.entities.FirebaseTokenEntity;
import com.kefiya.home.apis.users.entities.entities.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirebaseTokenCrudRepo extends JpaRepository<FirebaseTokenEntity,Long> {

    List<FirebaseTokenEntity> findByUserAndDeleted(UserLoginEntity user, Boolean deleted);
    List<FirebaseTokenEntity> findAllByUserAndSource(UserLoginEntity user, String source);
}
