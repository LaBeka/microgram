package com.example.microgram.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Follow {
    private Long followId;
    private Long userBeingFollowed; // followers
    private Long userFollowing;//following
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private LocalDate followDate;
}
