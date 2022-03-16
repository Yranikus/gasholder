package com.example.gasholder.entity;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomMapper implements RowMapper<PointJs> {
    @Override
    public PointJs mapRow(ResultSet rs, int rowNum) throws SQLException {
        Properties properties = new Properties();
        properties.setHintContent(rs.getString("name"));
        Geometry geometry = new Geometry();
        geometry.setCoordinates(rs.getDouble("latitude"),0);
        geometry.setCoordinates(rs.getDouble("longitude"),1);
        PointJs pointJs = new PointJs(rs.getInt("id"),geometry,properties);
        return pointJs;
    }
}
