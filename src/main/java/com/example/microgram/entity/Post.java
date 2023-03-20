package com.example.microgram.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class Post implements Likable {
    private int post_id;
    private int entity_id;
    private int user_id;
    private String photo;
    private String description;
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private Date postDate;

    @Override
    public LikableType getType() {
        return LikableType.POST;
    }
}
