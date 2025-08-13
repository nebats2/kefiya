package com.kefiya.home.apis.staffApi.services;

import com.kefiya.home.apis.staffApi.entities.StaffEntity;
import com.kefiya.home.apis.staffApi.entities.StaffRoleEntity;
import com.kefiya.home.apis.staffApi.entities.repos.StaffRoleSearchRepo;
import com.kefiya.home.apis.staffApi.entities.repos.StaffSearchRepo;
import com.kefiya.home.apis.staffApi.enums.StaffRolesEnum;
import com.kefiya.home.common.BaseException;
import com.kefiya.home.common.ErrorMessage;
import com.kefiya.home.services.controllers.ContextService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StaffAuthenticateService {
    private final StaffSearchRepo staffRepo;
    private final StaffRoleSearchRepo roleSearchRepo;
    private final ContextService contextService;

    public void authorizeProfileRole() throws BaseException {
        var staffId = contextService.getStaffId();
        var staff = validateAndGetStaff(staffId);
        if(!validateRole(List.of(StaffRolesEnum.PROFILE), staff)){
            throw new BaseException(ErrorMessage.user_has_no_permission);
        }
    }
    private boolean validateRole(List<StaffRolesEnum> roleName, StaffEntity staff) throws BaseException {
        var staffRoles = staff.getRoles().stream().map(StaffRoleEntity::getRole).toList();
        if(staffRoles.contains("SUPER_ADMIN") || staffRoles.contains("LEGEND")){
            return true;
        }
        return staffRoles.stream().anyMatch(roleName::contains);

    }

    private void validateRole(StaffRolesEnum roleName, StaffEntity staff) throws BaseException {
        var staffRoles = staff.getRoles().stream().map(StaffRoleEntity::getRole).toList();
        if(staffRoles.contains("SUPER_ADMIN") || staffRoles.contains("LEGEND")){
            return;
        }
        if(!staff.getRoles().stream().map(StaffRoleEntity::getRole).toList().contains(roleName.name())){
            throw new BaseException(ErrorMessage.user_has_no_permission);
        }

    }

    private StaffEntity validateAndGetStaff(Long staffId) throws BaseException {

         var staff = staffRepo.findByIdAndActive(staffId,true);
         if(staff == null || !staff.getActive()){
             throw new BaseException(ErrorMessage.entity_is_not_found, "Staff is not found");
         }
         var roles = staff.getRoles();

         if(roles ==null || roles.isEmpty()){
             throw new BaseException(ErrorMessage.user_has_no_permission, "Staff has no roles");
         }
         return staff;
    }
}
