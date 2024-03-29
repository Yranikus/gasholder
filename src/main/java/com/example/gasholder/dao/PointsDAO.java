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
        ArrayList<PointJs> pointJs = (ArrayList<PointJs>) jdbcTemplate.query("SELECT id, name, latitude, longitude, field, area, workshop FROM points ORDER BY 2 ASC", new CustomMapper());
        ArryOfPoints arryOfPoints = new ArryOfPoints();
        arryOfPoints.setFeatures(pointJs);
        return arryOfPoints;
    }

    public ArryOfPoints getPointsByWorkshop(String workshop){
        ArrayList<PointJs> pointJs = (ArrayList<PointJs>) jdbcTemplate.query("SELECT id, name, latitude, longitude, field, area, workshop FROM points WHERE workshop=? ORDER BY 2 ASC", new Object[]{workshop}, new CustomMapper());
        ArryOfPoints arryOfPoints = new ArryOfPoints();
        arryOfPoints.setFeatures(pointJs);
        return arryOfPoints;
    }

    public ArryOfPoints getPointsByNameLikeAndWorkshop(String workshop, String name){
        String serachName = name + "%%";
        ArrayList<PointJs> pointJs = (ArrayList<PointJs>) jdbcTemplate.query("SELECT id, name, latitude, longitude, field, area, workshop FROM points WHERE workshop=? AND name LIKE ?",
                new Object[]{workshop, serachName}, new CustomMapper());
        ArryOfPoints arryOfPoints = new ArryOfPoints();
        arryOfPoints.setFeatures(pointJs);
        return arryOfPoints;
    }

    public ArryOfPoints getPointsByNameLike(String name){
        String serachName = name + "%%";
        ArrayList<PointJs> pointJs = (ArrayList<PointJs>) jdbcTemplate.query("SELECT id, name, latitude, longitude, field, area, workshop FROM points WHERE name LIKE ?",
                new Object[]{serachName}, new CustomMapper());
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
