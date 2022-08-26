package com.example.gasholder.dao;

import com.example.gasholder.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReserviorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveReservoir(Point point){
        jdbcTemplate.update("INSERT INTO reservoir(name, latitude, longitude) VALUES (?,?,?)",
                point.getName(),
                point.getLatitude(),point.getLongitude());
    }

    public List<Point> getAll(){
        return jdbcTemplate.query("SELECT * FROM reservoir", new BeanPropertyRowMapper<>(Point.class));
    }


}
