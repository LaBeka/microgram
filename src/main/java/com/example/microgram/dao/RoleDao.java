package com.example.microgram.dao;


import com.example.microgram.dto.RegisterUserDto;
import com.example.microgram.entity.Roles;
import com.example.microgram.entity.User;
import com.example.microgram.mappers.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class RoleDao  {
    private final JdbcTemplate jdbcTemplate;

    public void createTable() {
        jdbcTemplate.execute("create table if not exists roles\n" +
                "(\n" +
                "    user_id    bigint references users(user_id),\n" +
                "    user_role varchar(50) not null" +
                ");");
    }

    public void dropTable() {
        jdbcTemplate.execute("drop table if exists roles cascade;");
    }

    public String setRoleOfUser(RegisterUserDto user, Roles roles){
        String sql = "insert into roles (user_id, user_role)\n" +
                "VALUES(?, ?);";
        int update = jdbcTemplate.update(sql, user.getUserId(), roles.toString());

        if(update == 1){
            return "And, Success to update role!";
        }
        return "But, not success to update role";
    }

    public List<Roles> getRolesByUserID(Long userId) {
        String sql = "select * from roles where user_id = ?;";
        return jdbcTemplate.query(sql, new RoleMapper(), userId);
    }
}
