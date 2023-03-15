package com.example.microgram.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Like {
    private LocalDate like_date;
    private User user_id;
    private LikableType type;
    private int entityId;

    public Like(LocalDate date, User user, Likable entity) {
        this.like_date = date;
        this.user_id = user;
        this.type = entity.getType();
        this.entityId = entity.getId();
    }
}