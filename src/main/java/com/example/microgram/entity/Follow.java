package com.example.microgram.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Follow {
    private Long followId;
    private int userBeingFollowed; // followers
    private int userFollowing;//following
    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private Date followDate;
}
