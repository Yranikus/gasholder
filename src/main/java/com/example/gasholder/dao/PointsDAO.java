package com.example.gasholder.dao;


import com.example.gasholder.entity.ArryOfPoints;
import com.example.gasholder.entity.CustomMapper;
import com.example.gasholder.entity.Point;
import com.example.gasholder.entity.PointJs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PointsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Point point){
        jdbcTemplate.update("INSERT INTO points(name, latitude, longitude) VALUES (?,?,?)", point.getName(),
                point.getLatitude(),point.getLongitude());
    }


    public ArryOfPoints getPoint(){
        ArrayList<PointJs> pointJs = (ArrayList<PointJs>) jdbcTemplate.query("SELECT * FROM points", new CustomMapper());
        ArryOfPoints arryOfPoints = new ArryOfPoints();
        arryOfPoints.setFeatures(pointJs);
        return arryOfPoints;
    }


}
