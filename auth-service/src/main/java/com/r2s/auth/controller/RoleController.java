package com.r2s.auth.controller;


import com.r2s.auth.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;

@RestController
@RequestMapping("/role")
@SecurityRequirement(name = "bearerAuth")
public class RoleController {


    @Autowired
    RoleService roleService;

    @GetMapping("/user")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> userAccess(){
        return roleService.userAccess();
        //return ResponseEntity.ok("hello user") ;
    }

    @GetMapping("/admin")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminAccess() {
        return roleService.adminAccess();
    }
    @GetMapping("/mod")
    //@PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<String> moderatorAccess(){
          return roleService.moderatorAccess() ;
    }

}
