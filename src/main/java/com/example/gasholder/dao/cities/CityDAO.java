package com.example.gasholder.dao.cities;

import com.example.gasholder.dao.DAOInterface;
import com.example.gasholder.entity.CityPoint;
import com.example.gasholder.entity.OilWellPoint;
import com.example.gasholder.entity.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CityDAO implements DAOInterface<CityPoint> {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List getAll() {
        return jdbcTemplate.query("SELECT * FROM points",new BeanPropertyRowMapper<>(OilWellPoint.class));
    }

    public CityDAO() {
        super();
    }


    @Override
    public void save(CityPoint cityPoint) {

    }

    @Override
    public void update(CityPoint cityPoint) {

    }

    @Override
    public void delete(CityPoint cityPoint) {

    }

    public CityPoint findnearest(OilWellPoint oilWellPoint){
        return jdbcTemplate.queryForObject("SELECT name, acos(sin(latitude)*sin(?) + cos(?)*cos(55.967909)*cos(56.965955 - ?)) * 111.1 as minimum FROM public.cities ORDER BY 2 ASC LIMIT 1;",
                new Object[]{oilWellPoint.getLatitude(),oilWellPoint.getLatitude(),oilWellPoint.getLongitude()}, new BeanPropertyRowMapper<>(CityPoint.class));
    }

}
