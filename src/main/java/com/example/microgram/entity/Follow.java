package com.example.microgram.entity;

import lombok.Data;

@Data
public class Follow implements Followable{
    private User user;
    private String followDate;

    @Override
    public void follow(User user) {

    }
}
