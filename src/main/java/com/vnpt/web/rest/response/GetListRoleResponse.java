package com.vnpt.web.rest.response;

import com.vnpt.entity.RoleEntity;
import org.springframework.data.domain.Page;

public class GetListRoleResponse {
    Page<RoleEntity> listRole;

    public Page<RoleEntity> getListRole() {
        return listRole;
    }

    public void setListRole(Page<RoleEntity> listRole) {
        this.listRole = listRole;
    }
}
