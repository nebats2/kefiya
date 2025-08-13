package com.kefiya.home.apis.users.entities.repos;

import com.kefiya.home.apis.users.entities.entities.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSearchRepo extends PagingAndSortingRepository<UserLoginEntity,Long>, JpaSpecificationExecutor<UserLoginEntity> {

    UserLoginEntity findByMobileAndDeleted(String mobile, Boolean deleted);

    UserLoginEntity findByIdAndDeleted(Long id, Boolean deleted);
}
