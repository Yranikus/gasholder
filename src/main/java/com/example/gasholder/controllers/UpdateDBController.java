package com.example.gasholder.controllers;

import com.example.gasholder.parser.Parcer;
import com.example.gasholder.service.OilWellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileInputStream;
import java.io.IOException;

@Controller("/update/")
public class UpdateDBController {

    @Autowired
    private OilWellService oilWellService;
    @Autowired
    private Parcer parcer;

    @GetMapping("/test")
    public String test(){
        oilWellService.setCityDirection();
        return "account";
    }

    @GetMapping("/test1")
    public String test2(){
        oilWellService.setReservoirDirection();
        return "account";
    }

    @GetMapping("/up")
    public String load(){
        return "index";
    }

    @GetMapping("upload")
    public void uploadPoints(){
        try {
            parcer.savePoints(new FileInputStream(
                    "C:\\Users\\yranikus\\Desktop\\gasholder\\src\\test\\java\\com\\example\\gasholder\\test.xlsm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("upload2")
    public void uploadReservoir(){
        try {
            parcer.saveReservoir(new FileInputStream(
                    "C:\\Users\\yranikus\\Desktop\\gasholder\\src\\test\\java\\com\\example\\gasholder\\water2.xlsm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("uploadcity")
    public void uploadCities(){
        try {
            parcer.saveCity(new FileInputStream(
                    "C:\\Users\\yranikus\\Desktop\\geg\\cities.xlsx"));
            System.out.println("kek");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
