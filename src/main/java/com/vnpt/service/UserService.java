package com.vnpt.service;

import com.vnpt.constans.Constansts;
import com.vnpt.constans.UserContansts;
import com.vnpt.entity.RoleEntity;
import com.vnpt.entity.UserEntity;
import com.vnpt.entity.UserRoleRelEntity;
import com.vnpt.repository.RoleRepository;
import com.vnpt.repository.UserRepository;
import com.vnpt.repository.UserRoleRelRepository;
import com.vnpt.service.dto.AccountDetail;
import com.vnpt.web.rest.request.CreateUserRequest;
import com.vnpt.web.rest.request.GetListUserRequest;
import com.vnpt.web.rest.request.UpdateUserRequest;
import com.vnpt.web.rest.response.BaseResponse;
import com.vnpt.web.rest.response.GetDetailUserResponse;
import com.vnpt.web.rest.response.GetListUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserRoleRelRepository userRoleRelRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       UserRoleRelRepository userRoleRelRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRelRepository = userRoleRelRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    public ResponseEntity<BaseResponse<GetDetailUserResponse>> createUser(CreateUserRequest request){

        //Check username
        UserEntity user = userRepository.findByUsername(request.getUsername());
        GetDetailUserResponse data = new GetDetailUserResponse();
        BaseResponse<GetDetailUserResponse> response = new BaseResponse<>();
        if(user==null){

            UserEntity newUser = new UserEntity();
            newUser.setFullName(request.getFullname());
            newUser.setUsername(request.getUsername());
            newUser.setPhone(request.getPhone());
            newUser.setEmail(request.getEmail());
            newUser.setStatus(UserContansts.USER_ACTIVE);

            String randomPass = this.genPassword();
            String encodePass = bCryptPasswordEncoder.encode(randomPass);

            //Do send email Password
            //TODO

            newUser.setPassword(encodePass);

            //Do set Create By
            newUser.setCreatedBy(UserContansts.SYSTEM); // TODO

            UserEntity userCreated = userRepository.save(newUser);

            List<String> roles = request.getRoles();

            roles.forEach((role) ->{
                UserRoleRelEntity ur = new UserRoleRelEntity();
                ur.setUserId(userCreated.getId());
                ur.setRoleId(role);
                ur.setCreatedBy(UserContansts.SYSTEM); // TODO
                userRoleRelRepository.save(ur);
            });
            data.setUserInfo(userCreated);
            data.setRoleInfo(roles);


            response.success(data);

        }else{
            response.setCode(Constansts.STATUS_500);
            response.setCode("Username has existed.");
        }

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<BaseResponse<GetDetailUserResponse>> updateUser(UpdateUserRequest request){
        GetDetailUserResponse data = new GetDetailUserResponse();
        BaseResponse<GetDetailUserResponse> response = new BaseResponse<>();


        //Check ID user
        Optional<UserEntity> otpOldUser = userRepository.findById(request.getUserId());
        if(!otpOldUser.isPresent()){
            response.setCode(Constansts.STATUS_500);
            response.setCode("ID user not exist");
        }else{
            UserEntity oldUser = otpOldUser.get();
            oldUser.setEmail(request.getEmail());
            oldUser.setFullName(request.getFullname());
            oldUser.setStatus(request.getStatus());
            oldUser.setLastModifiedBy(UserContansts.SYSTEM); //TODO

            data.setUserInfo(oldUser);
            //Update Role

            List<UserRoleRelEntity> oldRoles = userRoleRelRepository.findByUserId(oldUser.getId());

            if(!request.getRoles().isEmpty()){

                userRoleRelRepository.deleteAll(oldRoles);

                request.getRoles().forEach((role) ->{
                    UserRoleRelEntity ur = new UserRoleRelEntity();
                    ur.setUserId(oldUser.getId());
                    ur.setRoleId(role);
                    ur.setLastModifiedBy(UserContansts.SYSTEM); // TODO
                    userRoleRelRepository.save(ur);
                });
                data.setRoleInfo(request.getRoles());
            }
        }
        return ResponseEntity.ok(response);
    }

    public List<String> getListPermissionByUser(UserEntity user){
        return null;
    }


    private String genPassword() {
        String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
        String DIGITS = "0123456789";
        String SPECIAL_CHARS = "!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        password.append(UPPER_CASE.charAt(random.nextInt(UPPER_CASE.length())));
        for (int i = 0; i < 4; i++) {
            password.append(LOWER_CASE.charAt(random.nextInt(LOWER_CASE.length())));
        }
        for (int i = 0; i < 2; i++) {
            password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        }
        password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        return password.toString();
    }

}
