package com.example.microgram.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Like {
    private Long likeId;
    private Long userId;
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private LocalDate likeDate;
    private Long postId;
}