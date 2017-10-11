package com.cengha.divider.repository;

import com.cengha.divider.model.Role;
import com.cengha.divider.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<User,Long> {

    @Query("select r from Role r where r.name=:name")
    Optional<Role> findByName(@Param("name") String name);

    @Query("select r from Role r where r.id in (select ur.roleId from UserRole ur where ur.userId=:userId)")
    Set<Role> findByUserId(@Param("userId") Long userId);

}
