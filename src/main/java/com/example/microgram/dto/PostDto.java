package com.example.microgram.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PostDto {
    private int post_id;
    private int entity_id;
    private int user_id;
    private String photo;
    private String description;
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private Date postDate;
}
