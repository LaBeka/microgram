package com.example.microgram.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class Post implements Likable {
    private int entity_id;
    private int user_id;
    private String photo;
    private String description;
    private Date postDate;

    @Override
    public LikableType getType() {
        return LikableType.POST;
    }
}
