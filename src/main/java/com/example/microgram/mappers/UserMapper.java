package com.example.microgram.mappers;

import com.example.microgram.dao.RoleDao;
import com.example.microgram.entity.Roles;
import com.example.microgram.entity.User;
import com.example.microgram.utils.ApplicationContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper implements RowMapper<User> {
    //private RoleDao roleDao;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setAccountName(rs.getString("account_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("user_name"));

        RoleDao roleDao = ApplicationContextHolder.getContext().getBean(RoleDao.class);
        List<Roles> rolesByUserID = roleDao.getRolesByUserID(user.getUserId());
        user.setRoles(rolesByUserID);

        user.setEnabled(rs.getBoolean("enabled"));
        return user;
    }
}
