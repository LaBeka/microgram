package com.example.microgram.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post implements Likable {
    private int entity_id;
    private User user_id;
    private String photo;
    private String description;
    private LocalDate post_date;

    @Override
    public LikableType getType() {
        return LikableType.POST;
    }
}
