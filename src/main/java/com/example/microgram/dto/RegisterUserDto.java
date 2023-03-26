package com.example.microgram.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RegisterUserDto {
    private String accountName;
    private String email;
    private String password;
    private String name;

    public String validateUserData(){
        StringBuilder messageBack = new StringBuilder();
        String nextLine = "\n";
        if(accountName.isBlank() || accountName == null || accountName.length() < 2){
            messageBack.append("Account name is not valid");
            messageBack.append(nextLine);
        }
        if(email.isBlank() || email == null || email.length() < 5){
            messageBack.append("Email is not valid.");
            messageBack.append(nextLine);
        }
        if(password.isEmpty() || password.length() < 5 || password == null){
            messageBack.append("Password is not valid");
            messageBack.append(nextLine);
        }
        if(name.isEmpty() || name.length() < 3 || name == null){
            messageBack.append("User name is not valid");
            messageBack.append(nextLine);
        }

        if(messageBack.length() < 1) {
            this.password = md5String(password);
        }
        return messageBack.toString();
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
