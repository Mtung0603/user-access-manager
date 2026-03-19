package com.r2s.auth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;


@Service
public class RoleService {
    @PreAuthorize("hasRole('USERS')")
    public ResponseEntity<String> userAccess(){
        return ResponseEntity.ok("hello user") ;
    }


    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminAccess(){
        return ResponseEntity.ok("hello admin") ;
    }


    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<String> moderatorAccess(){
        return ResponseEntity.ok("hello MODERATOR") ;
    }

}

