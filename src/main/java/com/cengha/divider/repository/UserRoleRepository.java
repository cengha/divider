package com.cengha.divider.repository;

import com.cengha.divider.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole,Long> {

    @Query("SELECT ur.roleId FROM UserRole ur where ur.userId=:userId")
    Iterable<UserRole> findByUserId(@Param("userId") Long userId);
}
