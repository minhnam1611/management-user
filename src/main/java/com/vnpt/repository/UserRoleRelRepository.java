package com.vnpt.repository;

import com.vnpt.entity.UserRoleRelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRelRepository extends JpaRepository<UserRoleRelEntity, String> {
}
