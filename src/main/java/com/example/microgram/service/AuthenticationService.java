package com.example.microgram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private JdbcTemplate jdbcTemplate;
    private Connection conn;

    public void authenticateUser(String accountName,
                             String password) throws SQLException, ClassNotFoundException {

        String securedPassword = md5String(getCharacters(password).toString());
        Optional userExists = checkUser(accountName, securedPassword);

        if (userExists != null) {
            String message = "Successful!";
        } else {
            String message = "Not successful";
        }
    }
    private Optional checkUser(String accountName, String pass) throws SQLException{
            String sql = "select * from users \n" +
                    "where account_name :accountName\n" +
                    "and password :pass;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet res = ps.executeQuery(sql);

            return Optional.ofNullable(res.next());
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
