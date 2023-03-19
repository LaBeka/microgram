package com.example.microgram.service;

import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private JdbcTemplate jdbcTemplate;

    public void registerUser(String accountName,
                           String email,
                           String password,
                           String userName) throws SQLException, ClassNotFoundException {

        Optional userExists = checkUser(accountName);
        String pas = getCharacters(password).toString();
        String securedPassword = md5String(pas);

        if(userExists != null) {
            UserDto newUser = new UserDto(0, accountName, email, securedPassword, 0, 0, userName);
            String sql = "insert into users (account_name, email, password, post_quantity, follow_quantity, user_name)\n" +
                    "values (?, ?, ?, ?, ?, ?, ?);";
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, 0);
                ps.setString(2, newUser.getAccountName());
                ps.setString(3, newUser.getEmail());
                ps.setString(4, newUser.getPassword());
                ps.setInt(5, 0);
                ps.setInt(6, 0);
                ps.setString(7, newUser.getUserName());
                return ps;
            });
            String message = "Successfully created a new account!";
        } else {
            String message = "Account name's already takes, Create a new account name!";
        }
    }

    private Optional checkUser(String accountName) {
        String sql = "select * from users \n" +
                "where account_name = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), accountName)
        ));
    }

    public static String md5String(String pass) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BigInteger bigInteger = new BigInteger(1, digest);
        String m5dHex = bigInteger.toString(16);
        while(m5dHex.length() < 32)
            m5dHex = "0" + m5dHex;
        return m5dHex;
    }

    private List<Character> getCharacters(String pass){
        List<Character> newChar = new ArrayList<>();
        for(int i = 0; i < pass.length(); i++){
            newChar.add(pass.charAt(i));
        }
        return newChar;
    }
}
