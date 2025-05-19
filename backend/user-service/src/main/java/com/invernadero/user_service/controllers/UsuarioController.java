package com.invernadero.user_service.controllers;

import com.invernadero.user_service.dtos.AlarmaDTO;
import com.invernadero.user_service.dtos.CrearUsuarioDTO;
import com.invernadero.user_service.dtos.LoginDTO;
import com.invernadero.user_service.dtos.UsuarioDTO;
import com.invernadero.user_service.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody CrearUsuarioDTO crearUsuarioDTO) {
        UsuarioDTO nuevoUsuario = usuarioService.registrarUsuario(crearUsuarioDTO);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @GetMapping("/{email}")
    public ResponseEntity<LoginDTO> obtenerUsuarioPorEmail(@PathVariable String email) {
        LoginDTO usuario = usuarioService.obtenerPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/alerta/{id}")
    public ResponseEntity<AlarmaDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        AlarmaDTO usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }
}
