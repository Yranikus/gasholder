package com.example.gasholder.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class UIController {


    @GetMapping("/")
    public String login(){
        return "enter";
    }

    @GetMapping("/lk")
    public String lk(){
        return "account";
    }

}
