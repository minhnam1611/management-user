package com.vnpt.service;

import com.vnpt.entity.UserEntity;
import com.vnpt.repository.RoleRepository;
import com.vnpt.repository.UserRepository;
import com.vnpt.service.dto.AccountDetail;
import com.vnpt.web.rest.request.GetListUserRequest;
import com.vnpt.web.rest.response.BaseResponse;
import com.vnpt.web.rest.response.GetDetailUserResponse;
import com.vnpt.web.rest.response.GetListUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        List<String> lstPers = getListPermissionByUser(user);
        return user == null ? null : new AccountDetail(user, lstPers);
    }

    public ResponseEntity<BaseResponse<GetListUserResponse>> getListUser(GetListUserRequest request, Pageable pageable){
        GetListUserResponse data = new GetListUserResponse();
        String keySearch = "";
        if(request.getSearchKey().equals("")){
            keySearch = null;
        }else {
            keySearch = "%" +request.getSearchKey()+ "%";
        }
        Page<UserEntity> listUser = userRepository.getListUser(keySearch, pageable);
        data.setListUser(listUser);

        BaseResponse<GetListUserResponse> response = new BaseResponse<>();
        response.success(data);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<BaseResponse<GetDetailUserResponse>> getUserDetail(String id){
        GetDetailUserResponse data = new GetDetailUserResponse();
        Optional<UserEntity> userInfo = userRepository.findById(id);
        if(userInfo.isPresent()){
            data.setUserInfo(userInfo.get());
            List<String> listRole = userRepository.getRolesOfUser(userInfo.get().getId());
            data.setRoleInfo(listRole);
        }
        BaseResponse<GetDetailUserResponse> response = new BaseResponse<>();
        response.success(data);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<BaseResponse<String>> deleteUser(String id){
        BaseResponse<String> response = new BaseResponse<>();
        Optional<UserEntity> userInfo = userRepository.findById(id);
        if(userInfo.isPresent()) {
            userRepository.delete(userInfo.get());
            response.success("Deleted UserID: " + id);
        }else{
            response.setCode("200");
            response.setDesc("ID user not exist. Delete Fail");
        }
        return ResponseEntity.ok(response);
    }

    public List<String> getListPermissionByUser(UserEntity user){
        return null;
    }

}
