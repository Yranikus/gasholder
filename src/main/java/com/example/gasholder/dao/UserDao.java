package com.example.gasholder.dao;

import com.example.gasholder.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public UserEntity findByLogin(String login){
        return jdbcTemplate.queryForObject("SELECT * FROM users", new BeanPropertyRowMapper<>(UserEntity.class));
    }



}
