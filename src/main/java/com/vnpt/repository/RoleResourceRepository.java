package com.vnpt.repository;

import com.vnpt.entity.RoleResourceRelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleResourceRepository extends JpaRepository<RoleResourceRelEntity, String> {
}
