package com.example.microgram.dto;

import com.example.microgram.entity.LikableType;
import com.example.microgram.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LIkeDto {
    private LocalDate like_date;
    private User user_id;
    private LikableType type;
    private int entityId;
}
