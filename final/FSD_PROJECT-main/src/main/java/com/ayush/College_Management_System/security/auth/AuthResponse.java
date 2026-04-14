package com.ayush.College_Management_System.security.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String type;
    private String username;
    private String role;
}