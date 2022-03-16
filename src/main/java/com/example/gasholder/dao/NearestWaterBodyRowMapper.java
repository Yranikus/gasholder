package com.example.gasholder.dao;

import com.example.gasholder.entity.WaterPoint;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NearestWaterBodyRowMapper implements RowMapper<WaterPoint> {

    @Override
    public WaterPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new WaterPoint(rs.getString("name"),rs.getDouble("latitude"), rs.getDouble("longitude"),rs.getString("type"));
    }



}
