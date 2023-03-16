package com.example.microgram.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Comment implements Likable {
    private int id;
    private User user_id;
    private String commentText;
    private LocalDate commentDate;

    @Override
    public LikableType getType() {
        return LikableType.COMMENT;
    }
}
