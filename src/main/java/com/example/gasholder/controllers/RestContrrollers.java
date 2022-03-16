package com.example.gasholder.controllers;

//import com.example.gasholder.configuration.UserDetailServiceImpl;
import com.example.gasholder.dao.PointsDAO;
import com.example.gasholder.dao.UserDao;
import com.example.gasholder.entity.ArryOfPoints;
import com.example.gasholder.entity.UserEntity;
import com.example.gasholder.parser.Parcer;
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
    private UserDao userDao;
    @Autowired
    private Parcer parcer;


    @GetMapping("user")
    public UserEntity getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userDao.findByLogin(auth.getName());
    }

    @GetMapping("upload")
    public void uploadTeams(){
        try {
            parcer.savePoints(new FileInputStream(
                    "C:\\Users\\yranikus\\Desktop\\gasholder\\src\\test\\java\\com\\example\\gasholder\\test.xlsm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/getpoints")
    public ArryOfPoints getTeams(){
        return pointsDAO.getPoint();
    }


}
