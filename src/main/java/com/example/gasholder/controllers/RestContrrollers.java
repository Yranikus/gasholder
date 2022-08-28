package com.example.gasholder.controllers;

//import com.example.gasholder.configuration.UserDetailServiceImpl;
import com.example.gasholder.dao.PointsDAO;
import com.example.gasholder.dao.UserDao;
import com.example.gasholder.entity.ArryOfPoints;
import com.example.gasholder.entity.Discription;
import com.example.gasholder.entity.UserEntity;
import com.example.gasholder.parser.Parcer;
import com.example.gasholder.service.OilWellService;
import com.example.gasholder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("rest")
public class RestContrrollers {


    @Autowired
    private PointsDAO pointsDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private Parcer parcer;
    @Autowired
    private OilWellService oilWellService;


    @GetMapping("user")
    public UserEntity getCurrentUser(){
        return userService.findByName(SecurityContextHolder.getContext().getAuthentication());
    }

    @GetMapping("/getdiscription")
    public Discription getDiscription(@RequestParam int id){
       return pointsDAO.getDiscription(id);
    }

//    @GetMapping("/getpoints")
//    public ArryOfPoints getPoints(){
//        return oilWellService.getPoint();
//    }

    @GetMapping("/{workshop}")
    public ArryOfPoints getPointByWorkshop(@PathVariable String workshop){
        return oilWellService.getPointByWorkshop(workshop);
    }
}
