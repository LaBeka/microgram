package com.example.microgram.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Like {
    private LocalDate date;
    private User user;
    private LikableType type;
    private int entityId;

    public Like(LocalDate date, User user, Likable entity) {
        this.date = date;
        this.user = user;
        this.type = entity.getType();
        this.entityId = entity.getId();
    }
}