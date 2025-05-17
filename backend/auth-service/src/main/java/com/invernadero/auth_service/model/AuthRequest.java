package com.invernadero.auth_service.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String role;
    private Long userId;
    private String password;

    // Getters y setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}