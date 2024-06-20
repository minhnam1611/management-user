package com.vnpt.repository;

import com.vnpt.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    @Query(value = "SELECT u FROM UserEntity u " +
        "WHERE ( :keySearch is null or u.username like :keySearch ) " +
        "OR ( :keySearch is null or u.fullName like :keySearch )")
    Page<UserEntity> getListUser(@Param("keySearch") String keySearch,
                                    Pageable pageable);

    @Query(value = "select r.name from user u " +
        "inner join user_role_rel ur on u.id = ur.user_id " +
        "inner join role r on ur.role_id = r.id " +
        "where u.id = :id ", nativeQuery = true)
    List<String> getRolesOfUser(@Param("id") String userId);

}
