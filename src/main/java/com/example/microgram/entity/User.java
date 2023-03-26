package com.example.microgram.entity;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {
    private Long userId;
    private String accountName;
    private String email;
    private String password;
    private String name;
}
