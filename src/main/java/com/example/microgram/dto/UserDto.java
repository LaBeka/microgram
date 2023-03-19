package com.example.microgram.dto;

import lombok.Data;

@Data
public class UserDto {
    private int user_id;
    private String accountName;
    private String email;
    private String password;
    private Integer postQuantity;
    private Integer followQuantity;
    private String user_name;
}
