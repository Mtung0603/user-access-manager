package com.r2s.security;

import com.r2s.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username) ;

    void deleteByUsername(String username);

}
