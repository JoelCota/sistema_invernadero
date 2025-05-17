package com.invernadero.user_service.services.impl;// UsuarioServiceImpl.java
import com.invernadero.user_service.dtos.CrearUsuarioDTO;
import com.invernadero.user_service.dtos.LoginDTO;
import com.invernadero.user_service.dtos.UsuarioDTO;
import com.invernadero.user_service.exceptions.EmailAlreadyExistsException;
import com.invernadero.user_service.exceptions.UsuarioNotFoundException;
import com.invernadero.user_service.mappers.UsuarioMapper;
import com.invernadero.user_service.models.Usuario;
import com.invernadero.user_service.repository.UsuarioRepository;
import com.invernadero.user_service.services.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioDTO registrarUsuario(CrearUsuarioDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("El email ya está registrado: " + dto.getEmail());
        }
        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        Usuario guardado = usuarioRepository.save(usuario);
        System.out.println(guardado);
        return UsuarioMapper.toDTO(guardado);
    }

    @Override
    public LoginDTO obtenerPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con email: " + email));
        return UsuarioMapper.toLoginDTO(usuario);
    }

    @Override
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }
}
