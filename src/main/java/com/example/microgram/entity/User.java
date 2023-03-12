package com.example.microgram.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String accountName;
    private String email;
    private String password;
    private Integer postQuantity;
    private Integer followerQuantity;
}
