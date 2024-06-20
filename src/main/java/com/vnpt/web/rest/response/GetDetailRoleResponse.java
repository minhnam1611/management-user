package com.vnpt.web.rest.response;

import com.vnpt.entity.RoleEntity;
import com.vnpt.web.rest.dto.ResourceInfo;

import java.util.List;

public class GetDetailRoleResponse {
    private RoleEntity roleInfo;

    private List<ResourceInfo> srcInfo;

    public RoleEntity getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(RoleEntity roleInfo) {
        this.roleInfo = roleInfo;
    }

    public List<ResourceInfo> getSrcInfo() {
        return srcInfo;
    }

    public void setSrcInfo(List<ResourceInfo> srcInfo) {
        this.srcInfo = srcInfo;
    }
}
