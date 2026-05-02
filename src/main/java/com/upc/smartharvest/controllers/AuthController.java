package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.LoginDTO;
import com.upc.smartharvest.DTOS.LoginResponseDTO;
import com.upc.smartharvest.entities.Usuario;
import com.upc.smartharvest.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        if (loginDTO.getUsername() == null || loginDTO.getUsername().isBlank()
                || loginDTO.getPassword() == null || loginDTO.getPassword().isBlank()) {
            return new ResponseEntity<>("Debe ingresar usuario y contraseña", HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioRepository.findByUsername(loginDTO.getUsername()).orElse(null);

        if (usuario == null) {
            return new ResponseEntity<>("Usuario o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }

        if (!"ACTIVO".equalsIgnoreCase(usuario.getEstado())) {
            return new ResponseEntity<>("El usuario se encuentra inactivo", HttpStatus.FORBIDDEN);
        }

        if (!usuario.getPasswordHash().equals(loginDTO.getPassword())) {
            return new ResponseEntity<>("Usuario o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }

        LoginResponseDTO response = new LoginResponseDTO();
        response.setId(usuario.getId());
        response.setUsername(usuario.getUsername());
        response.setRol(usuario.getRol());
        response.setEstado(usuario.getEstado());
        response.setAgricultorId(usuario.getAgricultor().getId());
        response.setMensaje("Inicio de sesión correcto");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return new ResponseEntity<>("Sesión cerrada correctamente", HttpStatus.OK);
    }
}