package com.example.microgram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private int userId;
    private String accountName;
    private String email;
    private String password;
    private Integer postQuantity;
    private Integer followQuantity;
    private String userName;
}
