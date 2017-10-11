package com.cengha.divider.repository;

import com.cengha.divider.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    @Query("SELECT u FROM User u where u.email=:email")
    Optional<User> findUserByEmail(@Param("email") String email);
}
