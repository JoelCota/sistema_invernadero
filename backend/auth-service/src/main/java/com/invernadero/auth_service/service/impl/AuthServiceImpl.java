package com.invernadero.auth_service.service.impl;

import com.invernadero.auth_service.client.UserServiceClient;
import com.invernadero.auth_service.model.AuthRequest;
import com.invernadero.auth_service.model.AuthResponse;
import com.invernadero.auth_service.model.Usuario;
import com.invernadero.auth_service.service.AuthService;
import com.invernadero.auth_service.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserServiceClient userServiceClient, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userServiceClient = userServiceClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        Usuario usuario = userServiceClient.getUsuarioPorEmail(request.getEmail());
        System.out.println(usuario);
        System.out.println(userServiceClient.getUsuarioPorEmail(request.getEmail()));
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        // Valida password
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Genera token JWT
        String token = jwtService.generateToken(usuario.getEmail(), usuario.getRole(), usuario.getUserId());
        System.out.println(token);
        return new AuthResponse(token);
    }
}
