package com.example.microgram.entity;

import lombok.*;

@Data
@RequiredArgsConstructor
public class User {
    private int userId;
    private String accountName;
    private String email;
    private String password;
    private Integer postQuantity;
    private Integer followQuantity;
    private Integer followerQuantity;
    private String userName;
}
