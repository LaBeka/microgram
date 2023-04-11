package com.example.microgram.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Like {
    private Long likeId;
    private LikeType likeType;
    private Long userId;
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private LocalDate likeDate;
    private Long entityId;

    /*
    "likeType": "post",
    "userId": "2",
    "likeDate": "2023-01-03",
    "entity_id": 2
    */
}