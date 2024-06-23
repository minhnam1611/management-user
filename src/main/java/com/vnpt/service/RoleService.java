package com.vnpt.service;

import com.vnpt.entity.RoleEntity;
import com.vnpt.entity.UserEntity;
import com.vnpt.repository.ResourceRepository;
import com.vnpt.repository.RoleRepository;
import com.vnpt.repository.UserRepository;
import com.vnpt.web.rest.dto.ResourceInfo;
import com.vnpt.web.rest.request.GetListRequest;
import com.vnpt.web.rest.response.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final ResourceRepository resourceRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository, ResourceRepository resourceRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
    }

    public ResponseEntity<BaseResponse<GetListRoleResponse>> getListRole(GetListRequest request, Pageable pageable) {
        GetListRoleResponse data = new GetListRoleResponse();
        String keySearch = "";
        if (request.getSearchKey() == null || request.getSearchKey().isEmpty()) {
            keySearch = null;
        } else {
            keySearch = "%" + request.getSearchKey() + "%";
        }
        Page<RoleEntity> listRole = roleRepository.getListRole(keySearch, pageable);
        data.setData(listRole.getContent());
        data.setSizeOfPage(listRole.getSize());
        data.setRecordsTotal(listRole.getTotalElements());
        data.setRecordsFiltered((int) listRole.getTotalElements());
        data.setTotalPages(listRole.getTotalPages());
        data.setData(listRole.getContent());

        BaseResponse<GetListRoleResponse> response = new BaseResponse<>();
        response.success(data);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<BaseResponse<GetDetailRoleResponse>> getRoleDetail(String id) {
        GetDetailRoleResponse data = new GetDetailRoleResponse();
        Optional<RoleEntity> roleInfo = roleRepository.findById(id);
        if (roleInfo.isPresent()) {
            data.setRoleInfo(roleInfo.get());
            List<Object[]> listSrc = roleRepository.getRrcOfRole(id);
            if (listSrc.size() > 0) {
                List<ResourceInfo> srcInfo = new ArrayList<>();

                for (Object[] obj : listSrc) {
                    ResourceInfo resourceInfo = new ResourceInfo();
                    resourceInfo.setRoleCode((String) obj[0]);
                    resourceInfo.setRoleName((String) obj[1]);
                    resourceInfo.setSrcCode((String) obj[2]);
                    resourceInfo.setSrcName((String) obj[3]);
                    if (obj[4] != null) {
                        List<String> lstAction = List.of(String.valueOf(obj[4]).split(","));
                        resourceInfo.setAction(lstAction);
                    }

                    srcInfo.add(resourceInfo);
                }

                data.setSrcInfo(srcInfo);
            }
        }

        BaseResponse<GetDetailRoleResponse> response = new BaseResponse<>();
        response.success(data);
        return ResponseEntity.ok(response);
    }
}
