package com.invernadero.user_service.services;

import com.invernadero.user_service.dtos.AlarmaDTO;
import com.invernadero.user_service.dtos.CrearUsuarioDTO;
import com.invernadero.user_service.dtos.LoginDTO;
import com.invernadero.user_service.dtos.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO registrarUsuario(CrearUsuarioDTO crearUsuarioDTO);
    AlarmaDTO obtenerPorId(Long id);
    LoginDTO obtenerPorEmail(String email);
    List<UsuarioDTO> obtenerTodos();
}