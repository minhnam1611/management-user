package com.vnpt.web.rest;

import com.vnpt.service.UserService;
import com.vnpt.web.rest.request.GetListUserRequest;
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
    @Autowired
    private UserService userService;

    @PostMapping("/users-list")
    public ResponseEntity<BaseResponse<GetListUserResponse>> getAllUsers(@RequestBody GetListUserRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), 5 );
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


}
