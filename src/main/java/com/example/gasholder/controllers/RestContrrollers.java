package com.example.gasholder.controllers;

import com.example.gasholder.configuration.UserDetailServiceImpl;
import com.example.gasholder.dao.UserDao;
import com.example.gasholder.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class RestContrrollers {

    @Autowired
    private UserDao userDao;

    @GetMapping("user")
    public UserEntity getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userDao.findByLogin(auth.getName());
    }

}
