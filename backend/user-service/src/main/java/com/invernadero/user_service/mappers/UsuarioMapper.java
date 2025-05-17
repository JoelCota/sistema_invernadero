package com.invernadero.user_service.mappers;

import com.invernadero.user_service.dtos.CrearUsuarioDTO;
import com.invernadero.user_service.dtos.LoginDTO;
import com.invernadero.user_service.dtos.UsuarioDTO;
import com.invernadero.user_service.models.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setPassword(usuario.getPassword());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol());
        dto.setNumeroCelular(usuario.getNumeroCelular());
        return dto;
    }

    public static LoginDTO toLoginDTO(Usuario usuario) {
        if (usuario == null) return null;
        LoginDTO dto = new LoginDTO();
        dto.setPassword(usuario.getPassword());
        dto.setRole(usuario.getRol());
        dto.setUserId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        return dto;
    }

    public static Usuario toEntity(CrearUsuarioDTO dto) {
        if (dto == null) return null;
        return Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .rol(dto.getRol())
                .numeroCelular(dto.getNumeroCelular())
                .build();
    }
}
