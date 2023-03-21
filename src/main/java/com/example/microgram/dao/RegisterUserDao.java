package com.example.microgram.dao;

import com.example.microgram.dto.RegisterUserDto;
import com.example.microgram.dto.UserDto;
import com.example.microgram.entity.User;
import com.example.microgram.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RegisterUserDao {
    private JdbcTemplate jdbcTemplate;

    public Optional<User> ifUserExists(String sql, String email){
        return Optional.ofNullable(DataAccessUtils.singleResult(
                jdbcTemplate.query(sql, new UserMapper(), email)
        ));
    }
    public boolean registerUser(RegisterUserDto data, String sql) {
        String pas = getCharacters(data.getPassword()).toString();
        String securedPassword = md5String(pas);

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, data.getAccountName());
            ps.setString(2, data.getEmail());
            ps.setString(3, securedPassword);
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            ps.setString(7, data.getUserName());
            return ps;
        });
        return true;
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
