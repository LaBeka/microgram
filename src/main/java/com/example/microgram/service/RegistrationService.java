package com.example.microgram.service;

import com.example.microgram.dto.RegisterUserDto;
import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationService {
    private JdbcTemplate jdbcTemplate;

    public boolean registerUser(String accountName,
                           String email,
                           String password,
                           String userName) {

        String pas = getCharacters(password).toString();
        String securedPassword = md5String(pas);

        String sql = "insert into users (account_name, " +
                "email, " +
                "password, " +
                "post_quantity, " +
                "follow_quantity, " +
                "user_name)\n" +
                "values (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, accountName);
            ps.setString(2, email);
            ps.setString(3, securedPassword);
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setString(6, userName);
            return ps;
        });
        return true;
    }

    public Optional<User> findUserByEmail(String email) {
        String sql = "select * from users \n" +
                "where email = ?;";
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), email)
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
