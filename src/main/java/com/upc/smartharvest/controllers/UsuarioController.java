package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.UsuarioDTO;
import com.upc.smartharvest.entities.*;
import com.upc.smartharvest.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return new ResponseEntity<>(usuarioService.listar().stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(toDTO(usuarioService.obtenerPorId(id)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioDTO dto) {
        Usuario entity = new Usuario();
        copyToEntity(dto, entity);
        return new ResponseEntity<>(toDTO(usuarioService.registrar(entity)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        try {
            Usuario entity = new Usuario();
            copyToEntity(dto, entity);
            return new ResponseEntity<>(toDTO(usuarioService.actualizar(id, entity)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            usuarioService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioDTO> findUsernameUsername(@PathVariable String username) {
        return usuarioService.buscarPorUsername(username)
                        .map(u -> new ResponseEntity<>(toDTO(u), HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/agricultor/{agricultorId}")
    public ResponseEntity<List<UsuarioDTO>> findAgricultorAgricultorId(@PathVariable Long agricultorId) {
        return new ResponseEntity<>(usuarioService.listarPorAgricultor(agricultorId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<UsuarioDTO>> findEstadoEstado(@PathVariable String estado) {
        return new ResponseEntity<>(usuarioService.listarPorEstado(estado).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    private UsuarioDTO toDTO(Usuario entity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setAgricultorId(entity.getAgricultor() != null ? entity.getAgricultor().getId() : null);
        dto.setUsername(entity.getUsername());
        // No se devuelve passwordHash por seguridad.
        dto.setRol(entity.getRol());
        dto.setEstado(entity.getEstado());
        dto.setUltimaAcceso(entity.getUltimaAcceso());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private void copyToEntity(UsuarioDTO dto, Usuario entity) {
        if (dto.getAgricultorId() != null) {
            Agricultor relation = new Agricultor();
            relation.setId(dto.getAgricultorId());
            entity.setAgricultor(relation);
        } else {
            entity.setAgricultor(null);
        }
        entity.setUsername(dto.getUsername());
        if (dto.getPasswordHash() != null) {
            entity.setPasswordHash(dto.getPasswordHash());
        }
        entity.setRol(dto.getRol());
        entity.setEstado(dto.getEstado());
        entity.setUltimaAcceso(dto.getUltimaAcceso());
    }
}
