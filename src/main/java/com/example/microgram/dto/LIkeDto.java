package com.example.microgram.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class LIkeDto {
    private Long likeId;
    private Long userId;
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private LocalDate likeDate;
    private Long postId;
}
