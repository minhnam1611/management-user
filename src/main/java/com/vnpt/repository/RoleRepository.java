package com.vnpt.repository;

import com.vnpt.entity.RoleEntity;
import com.vnpt.entity.UserEntity;
import com.vnpt.web.rest.dto.ResourceInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {
    @Query(value = "SELECT r FROM RoleEntity r " +
        "WHERE ( :keySearch is null or r.name like :keySearch ) " +
        "OR ( :keySearch is null or r.code like :keySearch )")
    Page<RoleEntity> getListRole(@Param("keySearch") String keySearch,
                                 Pageable pageable);

    @Query(value = "select r.code as role_code, r.name as role_name, src.code as src_code , src.name as src_name, rr.action " +
        "from role r inner join role_resource_rel rr on r.id = rr.role_id " +
        "inner join resource src on rr.resource_id = src.id " +
        "where r.id = :id ", nativeQuery = true)
    List<Object[]> getRrcOfRole(@Param("id") String id);
}
