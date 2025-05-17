package com.invernadero.auth_service.service;

import com.invernadero.auth_service.model.AuthRequest;
import com.invernadero.auth_service.model.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest request);
}
