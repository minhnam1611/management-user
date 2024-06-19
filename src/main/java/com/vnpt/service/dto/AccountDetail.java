package com.vnpt.service.dto;

import com.vnpt.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDetail implements UserDetails {

    private UserEntity user;

    private List<String> listGrants;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.listGrants.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    public AccountDetail(UserEntity user, List<String> listGrants) {
        this.user = user;
        this.listGrants = listGrants;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<String> getListGrants() {
        return listGrants;
    }

    public void setListGrants(List<String> listGrants) {
        this.listGrants = listGrants;
    }
}
