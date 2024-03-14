package com.example.payload;

import lombok.Data;

@Data
public class SignInDto {
    private String name;
    private String email;
    private String username;
    private String password;

}
