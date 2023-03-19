package com.example.microgram.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Like {
    private LocalDate like_date;
    private User user_id;
    private LikableType type_like;
    private int entity_id;

    public Like(LocalDate date, User user, Likable entity) {
        this.like_date = date;
        this.user_id = user;
        this.type_like = entity.getType();
        this.entity_id = entity.getEntity_id();
    }
}