package com.vnpt.repository;

import com.vnpt.entity.ResourceEntity;
import com.vnpt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, String> {
}
