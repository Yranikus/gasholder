package com.example.gasholder.entity;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomMapper implements RowMapper<PointJs> {
    @Override
    public PointJs mapRow(ResultSet rs, int rowNum) throws SQLException {
        Properties properties = new Properties();
        properties.setIconContent(rs.getString("name"));
        properties.setIconCaption(rs.getString("name"));
        properties.setHintContent("id: " + rs.getString("name") + "<br/>Месторождение: " + rs.getString("field") + "<br/>Площадь: " + rs.getString("area")
        +"<br/>Цех: "  + rs.getString("workshop"));
        Geometry geometry = new Geometry();
        geometry.setCoordinates(rs.getDouble("latitude"),0);
        geometry.setCoordinates(rs.getDouble("longitude"),1);
        int[] imgSize = {60, 45};
        int[] imgOffset = {-18, -12};
        Options options = new Options("default#imageWithContent", "img/oil.png", imgSize,imgOffset);
        PointJs pointJs = new PointJs(rs.getInt("id"),geometry,properties, options);
        pointJs.setName(rs.getString("name"));
        return pointJs;
    }
}
