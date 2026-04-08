package com.r2s.user.repository;


import com.r2s.user.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username) ;


        @Transactional
        void deleteByUsername(String username);

        boolean existsByUsername(String username) ;
    }


