package com.example.microgram.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@AllArgsConstructor
public class RegisterUserDto {
    private Long userId;
    @NonNull
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
        if(accountName == null || accountName.isBlank() || accountName.length() < 2){
            messageBack.append("Account name is not valid. ");
        }
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
