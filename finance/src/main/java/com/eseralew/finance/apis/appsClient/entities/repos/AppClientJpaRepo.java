package com.kefiya.home.apis.appsClient.entities.repos;

import com.kefiya.home.apis.appsClient.entities.AppClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppClientJpaRepo extends JpaRepository<AppClientEntity, Long> {

    AppClientEntity findByClientIdAndEnabled(Integer clientId, Boolean enabled);

    List<AppClientEntity> findAllByEnabled(Boolean enabled);
}
