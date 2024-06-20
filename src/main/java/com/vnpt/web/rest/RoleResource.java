package com.vnpt.web.rest;

import com.vnpt.service.RoleService;
import com.vnpt.web.rest.request.GetListRequest;
import com.vnpt.web.rest.response.BaseResponse;
import com.vnpt.web.rest.response.GetDetailRoleResponse;
import com.vnpt.web.rest.response.GetDetailUserResponse;
import com.vnpt.web.rest.response.GetListRoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/role")
public class RoleResource {

    private final int PAGE_SIZE = 5;

    @Autowired
    private RoleService roleService;

    @PostMapping("/role-list")
    public ResponseEntity<BaseResponse<GetListRoleResponse>> getAllUsers(@RequestBody GetListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), PAGE_SIZE );
        return roleService.getListRole(request, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<GetDetailRoleResponse>> getDetailRole(@PathVariable String id){
        return roleService.getRoleDetail(id);
    }

}
