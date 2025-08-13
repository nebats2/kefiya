package com.kefiya.home.apis.staffApi.entities.repos;

import com.kefiya.home.apis.staffApi.entities.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffSearchRepo extends JpaRepository<StaffEntity, Long> {

    StaffEntity findByIdAndActive(Long id, Boolean active);

}
