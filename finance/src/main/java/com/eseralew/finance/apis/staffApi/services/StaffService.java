package com.kefiya.home.apis.staffApi.services;

import com.kefiya.home.apis.staffApi.entities.StaffEntity;
import com.kefiya.home.apis.staffApi.entities.repos.StaffRoleSearchRepo;
import com.kefiya.home.apis.staffApi.entities.repos.StaffSearchRepo;
import com.kefiya.home.common.BaseException;
import com.kefiya.home.common.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StaffService {
    private final StaffSearchRepo staffSearchRepo;
    private final StaffRoleSearchRepo roleSearchRepo;


    public StaffEntity validateAndGetStaff(Long staffId) throws BaseException {
         var staff = staffSearchRepo.findByIdAndActive(staffId,true);
         if(staff == null || !staff.getActive()){
             throw new BaseException(ErrorMessage.entity_is_not_found, "Staff is not found");
         }
         var roles = roleSearchRepo.findAllByStaffAndEnabled(staff,true);

         if(roles == null || roles.isEmpty()){
             System.out.println("staff has no roles");
             throw new BaseException(ErrorMessage.entity_is_not_found, "Staff is not found");
         }
         return staff;
    }
}
