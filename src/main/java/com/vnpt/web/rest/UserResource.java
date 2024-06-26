package com.vnpt.web.rest;

import com.vnpt.service.UserService;
import com.vnpt.web.rest.request.CreateUserRequest;
import com.vnpt.web.rest.request.GetListRequest;
import com.vnpt.web.rest.request.UpdateUserRequest;
import com.vnpt.web.rest.response.BaseResponse;
import com.vnpt.web.rest.response.GetDetailUserResponse;
import com.vnpt.web.rest.response.GetListUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserResource {

    private final int PAGE_SIZE = 10;

    @Autowired
    private UserService userService;

    @PostMapping("/users-list")
    public ResponseEntity<BaseResponse<GetListUserResponse>> getAllUsers(@RequestBody GetListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), PAGE_SIZE );
        return userService.getListUser(request,pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<GetDetailUserResponse>> getDetailUser(@PathVariable String id){
        return userService.getUserDetail(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<GetDetailUserResponse>> createUser(@RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

    @PostMapping("/update")
    public ResponseEntity<BaseResponse<GetDetailUserResponse>> update(@RequestBody UpdateUserRequest request){
        return userService.updateUser(request);
    }
}
