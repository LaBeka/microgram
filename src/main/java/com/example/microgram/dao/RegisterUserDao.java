package com.example.microgram.dao;

import com.example.microgram.dto.user.RegUserFrontDto;
import com.example.microgram.dto.user.RegisterUserDto;
import com.example.microgram.entity.Roles;
import com.example.microgram.mappers.RegisterUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterUserDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private RegisterUserMapper regUserMapper;
    @Autowired
    private RoleDao roleDao;
    public void dropTable() {
        jdbcTemplate.execute("drop table if exists customers cascade;");
    }
    public void createTable() {
        jdbcTemplate.execute("create table if not exists users\n" +
                "(\n" +
                "    user_id    bigserial primary key,\n" +
                "    account_name    varchar(50) not null,\n" +
                "    email varchar(50) not null unique ,\n" +
                "    password text not null, \n" +
                "    user_name  varchar(50) not null,\n" +
                "    enabled boolean default true\n" +
                ");");
    }

    public Optional<RegUserFrontDto> ifIdenticalUserExists(RegisterUserDto userDto){
        String sql = "select * from users where email = ? and account_name = ? and password = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, regUserMapper, userDto.getEmail(), userDto.getAccountName(), userDto.getPassword())
        ));
    }

    public Optional<RegUserFrontDto> createNewUser(RegisterUserDto newUser) {
        String sql = "insert into users(" +
                "account_name, " +
                "email, " +
                "password, " +
                "user_name, " +
                "enabled)\n" +
                "values (?, ?, ?, ?, ?);";
        int update = jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newUser.getAccountName());
            ps.setString(2, newUser.getEmail());
            ps.setString(3, newUser.getPassword());
            ps.setString(4, newUser.getName());
            ps.setBoolean(5, Boolean.TRUE);
            return ps;
        });

        if (update == 1){
            Optional<RegUserFrontDto> user = getUserByEmail(newUser.getEmail());
            String messageBack = roleDao.setRoleOfUser(user.get(), Roles.USER);
            return user;
        }
        return Optional.empty();
    }
    public Optional<RegUserFrontDto> getUserByEmail(String email){
        String sql = "select * from users where email = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, regUserMapper, email)
        ));
    }

}
