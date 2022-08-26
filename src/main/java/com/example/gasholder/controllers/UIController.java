package com.example.gasholder.controllers;

import com.example.gasholder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller("/")
public class UIController {

    @Autowired
    private UserService userService;

    @GetMapping("/l")
    public String login(){
        return "enter";
    }

    @GetMapping("/")
    public String lk(@CookieValue(value = "workshop", required = false) Cookie workshop , HttpServletResponse response){
        userService.getWorkshop(SecurityContextHolder.getContext().getAuthentication(), response);
        return "account";
    }

    @GetMapping("/map")
    public String map(){
        return "map";
    }


}
