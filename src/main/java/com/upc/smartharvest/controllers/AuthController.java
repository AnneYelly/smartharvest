package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.LoginDTO;
import com.upc.smartharvest.DTOS.LoginResponseDTO;
import com.upc.smartharvest.Security.PasswordUtil;
import com.upc.smartharvest.Security.TokenUtil;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {

        if (loginDTO == null
                || loginDTO.getUsername() == null
                || loginDTO.getUsername().isBlank()
                || loginDTO.getPassword() == null
                || loginDTO.getPassword().isBlank()) {
            throw new RuntimeException("Debe ingresar usuario y contraseña");
        }

        Usuario usuario = usuarioRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos"));

        if (!"ACTIVO".equalsIgnoreCase(usuario.getEstado())) {
            throw new RuntimeException("El usuario se encuentra inactivo");
        }

        boolean passwordCorrecto = PasswordUtil.verificarPassword(
                loginDTO.getPassword(),
                usuario.getPasswordHash()
        );

        if (!passwordCorrecto) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        String token = TokenUtil.generarToken(usuario.getUsername(), usuario.getRol());

        LoginResponseDTO response = new LoginResponseDTO();
        response.setId(usuario.getId());
        response.setUsername(usuario.getUsername());
        response.setRol(usuario.getRol());
        response.setEstado(usuario.getEstado());
        response.setAgricultorId(
                usuario.getAgricultor() != null ? usuario.getAgricultor().getId() : null
        );
        response.setMensaje("Inicio de sesión correcto");
        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("Sesión cerrada correctamente", HttpStatus.OK);
    }
}