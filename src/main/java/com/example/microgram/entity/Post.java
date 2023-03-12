package com.example.microgram.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Post implements Likable {
    private int id;
    private String photo;
    private String description;
    private LocalDate postDate;

    @Override
    public LikableType getType() {
        return LikableType.POST;
    }
}
