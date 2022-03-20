package com.example.gasholder.controllers;

import com.example.gasholder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

@Controller("/")
public class UIController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String login(){
        return "enter";
    }

    @GetMapping("/lk")
    public String lk(@CookieValue(value = "workshop", required = false) Cookie workshop , HttpServletResponse response){
        if (workshop == null){
            userService.getWorkshop(SecurityContextHolder.getContext().getAuthentication(), response);
        }
        return "account";
    }

    @GetMapping("/up")
    public String load(){
        return "index";
    }

    @GetMapping("/map")
    public String map(){
        return "map";
    }


}
