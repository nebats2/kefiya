package com.kefiya.home.apis.staffApi.entities.repos;

 import com.kefiya.home.apis.staffApi.entities.StaffEntity;
 import com.kefiya.home.apis.staffApi.entities.StaffRoleEntity;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRoleSearchRepo extends JpaRepository<StaffRoleEntity,Long> {

    List<StaffRoleEntity> findAllByStaffAndEnabled(StaffEntity staff, Boolean enabled);
}
