package com.invernadero.auth_service.model;

public class Usuario {
    private Long userId;
    private String email;
    private String password;
    private String role;

    // Constructor vacío
    public Usuario() {}

    public String getPassword() { return password;}
    public void setPassword(String password) {this.password = password;}

    // Getters y Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
