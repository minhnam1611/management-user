package com.vnpt.web.rest.response;

import com.vnpt.entity.UserEntity;
import org.springframework.data.domain.Page;

public class GetListUserResponse {
    Page<UserEntity> listUser;

    public Page<UserEntity> getListUser() {
        return listUser;
    }

    public void setListUser(Page<UserEntity> listUser) {
        this.listUser = listUser;
    }


}
