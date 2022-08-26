package com.example.gasholder.dao;

import com.example.gasholder.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public UserEntity findByLogin(String login){
        return jdbcTemplate.queryForObject("SELECT * FROM users where login=?", new Object[]{login}, new BeanPropertyRowMapper<>(UserEntity.class));
    }

    public String getWorkshop(String login){
        return jdbcTemplate.queryForObject("SELECT department FROM users WHERE login=?",new Object[]{login}, String.class);
    }


}
