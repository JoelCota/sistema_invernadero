package com.invernadero.auth_service.model;

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token=token;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }
}