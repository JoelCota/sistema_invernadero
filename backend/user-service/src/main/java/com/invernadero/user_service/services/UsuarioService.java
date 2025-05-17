package com.invernadero.user_service.services;

import com.invernadero.user_service.dtos.CrearUsuarioDTO;
import com.invernadero.user_service.dtos.LoginDTO;
import com.invernadero.user_service.dtos.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO registrarUsuario(CrearUsuarioDTO crearUsuarioDTO);
    LoginDTO obtenerPorEmail(String email);
    List<UsuarioDTO> obtenerTodos();
}