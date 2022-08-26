package com.example.gasholder.dao;


import com.example.gasholder.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PointsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Point point, Discription discription){
        jdbcTemplate.update("INSERT INTO points(name, latitude, longitude, field, area, AGZU, workshop) VALUES (?,?,?,?,?,?,?)",
                point.getName(),
                point.getLatitude(),point.getLongitude(), discription.getField(),discription.getArea(),discription.getAGZU(),
                discription.getWorkshop());
    }

    public Discription getDiscription(int id){
        return jdbcTemplate.queryForObject("SELECT field, area, AGZU, workshop, city, distance, direction, reservior, reservior_distance, reservior_direction " +
                "FROM points WHERE id= ?", new Object[]{id}, new BeanPropertyRowMapper<>(Discription.class));
    }


    public ArryOfPoints getPoint(){
        ArrayList<PointJs> pointJs = (ArrayList<PointJs>) jdbcTemplate.query("SELECT id, name, latitude, longitude, field, area FROM points", new CustomMapper());
        ArryOfPoints arryOfPoints = new ArryOfPoints();
        arryOfPoints.setFeatures(pointJs);
        return arryOfPoints;
    }

    public ArryOfPoints getPointsByWorkshop(String workshop){
        ArrayList<PointJs> pointJs = (ArrayList<PointJs>) jdbcTemplate.query("SELECT id, name, latitude, longitude, field, area FROM points WHERE workshop=?", new Object[]{workshop}, new CustomMapper());
        ArryOfPoints arryOfPoints = new ArryOfPoints();
        arryOfPoints.setFeatures(pointJs);
        return arryOfPoints;
    }


    public List<Point> test(){
        return jdbcTemplate.query("SELECT id, name, latitude, longitude FROM points", new BeanPropertyRowMapper<>(Point.class));
    }

    public void saveNearestCity(String name, double distance, String direction, int id){
        jdbcTemplate.update("UPDATE points SET city=?, distance=?, direction=? WHERE id=?", name,distance,direction,id);
    }

    public void saveNearestReservoir(String name, double distance, String direction, int id){
        jdbcTemplate.update("UPDATE points SET reservior=?, reservior_distance=?, reservior_direction=? WHERE id=?", name,distance,direction,id);
    }

}
