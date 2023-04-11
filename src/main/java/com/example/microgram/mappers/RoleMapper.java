package com.example.microgram.mappers;


import com.example.microgram.entity.Roles;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class RoleMapper implements RowMapper<Roles> {
    @Override
    public Roles mapRow(ResultSet rs, int rowNum) throws SQLException {
        String role = rs.getString("user_role");
        return Roles.valueOf(role);
    }
}
