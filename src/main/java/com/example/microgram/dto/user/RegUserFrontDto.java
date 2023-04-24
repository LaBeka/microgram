package com.example.microgram.dto.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
public class RegUserFrontDto {
    private Long userId;
    private String email;
    private String name;
    private String accountName;
}
