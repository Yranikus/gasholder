package com.example.gasholder.service;

import com.example.gasholder.dao.UserDao;
import com.example.gasholder.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void getWorkshop(Authentication auth, HttpServletResponse response){
        Cookie cookie = new Cookie("workshop", userDao.getWorkshop(auth.getName()));
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
    }

    public UserEntity findByName(Authentication auth){
        return userDao.findByLogin(auth.getName());
    }
}
