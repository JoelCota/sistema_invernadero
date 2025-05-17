package com.itson.invernadero.service;

import com.itson.invernadero.model.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);
    Usuario buscarPorEmail(String email);
}

