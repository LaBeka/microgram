package com.example.microgram.service;

import com.example.microgram.dao.UserDao;
import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import com.example.microgram.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    public boolean addNewUser(User user) throws SQLException {
        String sql = "insert into users (account_name, email, password, post_quantity, follow_quantity, follower_quantity, user_name)\n" +
                "values (?, ?, ?, ?, ?, ?, ?);";
        return userDao.updateTable(user, sql);
    }

    public UserDto getUserById(int entityId){
        String sql = "select * from users where user_id = ?;";
        User userFound = userDao.findUserById(entityId, sql);
        return buildUser(userFound);

    }
    public UserDto getUserByAccountName(String accountName){
        String sql = "select * from users where account_name = ?;";
        User userFound = userDao.findUserByAcName(accountName, sql);
        return buildUser(userFound);
    }

    private UserDto buildUser(User userFound) {
        return UserDto.builder()
                .accountName(userFound.getAccountName())
                .email(userFound.getEmail())
                .postQuantity(userFound.getPostQuantity())
                .followQuantity(userFound.getFollowQuantity())
                .followerQuantity(userFound.getFollowerQuantity())
                .userName(userFound.getUserName())
                .build();
    }

    public UserDto getUserByEmail(String email){
        String sql = "select * from users where email = ?;";
        User userFound = userDao.findUserByEmail(email, sql);
        return buildUser(userFound);
    }

    public UserDto getUserByName(String userName){
        String sql = "select * from users where user_name = ?;";
        User userFound = userDao.findUserByName(userName, sql);
        return buildUser(userFound);
    }

    public Optional deleteUserByEmail(String email){
        String sql = "select * from users where email = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), email)
        ));
    }
    public String deleteAll() {
//        try{
            String sql = "delete * from users;";
//            PreparedStatement ps = jdbcTemplate
//            jdbcTemplate.execute(sql, new PreparedStatement());

            return "All is deleted!";
        /*} catch (SQLException e){
            return e.getMessage();
        }*/
    }

    public List<UserDto> getUsers(){
        String sql = "select * from users";
        List<User> query = jdbcTemplate.query(sql, new UserMapper());
        return query.stream()
            .map(u -> buildUser(u))
            .collect(Collectors.toList());
    }
}
