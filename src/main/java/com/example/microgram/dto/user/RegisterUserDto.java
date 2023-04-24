package com.example.microgram.dto.user;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private Long userId;
    private String accountName;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String name;
    private boolean enabled;

    public String validateUserData(){
        StringBuilder messageBack = new StringBuilder();
        if(email == null || email.isBlank() || email.length() < 5){
            messageBack.append("Email is not valid. ");
        }
        if(password == null || password.isEmpty() || password.length() < 5){
            messageBack.append("Password is not valid. ");
        }
        if(name == null || name.isEmpty() || name.length() < 3){
            messageBack.append("User name is not valid. ");
        }
        return messageBack.toString();
    }

}
