package com.example.gasholder.dao;

import com.example.gasholder.entity.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CityDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void saveCity(Point city){
        jdbcTemplate.update("INSERT INTO cities(name, latitude, longitude) VALUES (?,?,?)", city.getName(),
                city.getLatitude(), city.getLongitude());
    }

    public List<Point> getAllCity(){
        return jdbcTemplate.query("SELECT * FROM cities", new BeanPropertyRowMapper<>(Point.class));
    }

}
