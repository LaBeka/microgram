package com.example.microgram.entity;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private int userId;
    private String accountName;
    private String email;
    private String password;
    private Integer postQuantity;
    private Integer followQuantity;
    private String userName;
}
