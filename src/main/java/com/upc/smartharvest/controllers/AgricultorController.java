package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.AgricultorDTO;
import com.upc.smartharvest.entities.*;
import com.upc.smartharvest.services.AgricultorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/agricultores")
@RequiredArgsConstructor
public class AgricultorController {

    private final AgricultorService agricultorService;

    @GetMapping
    public ResponseEntity<List<AgricultorDTO>> findAll() {
        return new ResponseEntity<>(agricultorService.listar().stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgricultorDTO> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(toDTO(agricultorService.obtenerPorId(id)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/perfil")
    public ResponseEntity<AgricultorDTO> obtenerPerfil(@PathVariable Long id) {
        try {
            Agricultor agricultor = agricultorService.obtenerPorId(id);
            return new ResponseEntity<>(toDTO(agricultor), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/perfil")
    public ResponseEntity<AgricultorDTO> actualizarPerfil(
            @PathVariable Long id,
            @RequestBody AgricultorDTO dto) {
        try {
            Agricultor entity = new Agricultor();
            entity.setEmail(dto.getEmail());
            entity.setTelefono(dto.getTelefono());

            Agricultor actualizado = agricultorService.actualizarPerfil(id, entity);

            return new ResponseEntity<>(toDTO(actualizado), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<AgricultorDTO> create(@RequestBody AgricultorDTO dto) {
        Agricultor entity = new Agricultor();
        copyToEntity(dto, entity);
        return new ResponseEntity<>(toDTO(agricultorService.registrar(entity)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgricultorDTO> update(@PathVariable Long id, @RequestBody AgricultorDTO dto) {
        try {
            Agricultor entity = new Agricultor();
            copyToEntity(dto, entity);
            return new ResponseEntity<>(toDTO(agricultorService.actualizar(id, entity)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            agricultorService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<List<AgricultorDTO>> findActivos() {
        return new ResponseEntity<>(agricultorService.listarActivos().stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<AgricultorDTO> findDniDni(@PathVariable String dni) {
        return agricultorService.buscarPorDni(dni)
                        .map(a -> new ResponseEntity<>(toDTO(a), HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AgricultorDTO> findEmailEmail(@PathVariable String email) {
        return agricultorService.buscarPorEmail(email)
                        .map(a -> new ResponseEntity<>(toDTO(a), HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private AgricultorDTO toDTO(Agricultor entity) {
        AgricultorDTO dto = new AgricultorDTO();
        dto.setId(entity.getId());
        dto.setDni(entity.getDni());
        dto.setRuc(entity.getRuc());
        dto.setNombres(entity.getNombres());
        dto.setApellidos(entity.getApellidos());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setDepartamento(entity.getDepartamento());
        dto.setProvincia(entity.getProvincia());
        dto.setDistrito(entity.getDistrito());
        dto.setActivo(entity.getActivo());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private void copyToEntity(AgricultorDTO dto, Agricultor entity) {
        entity.setDni(dto.getDni());
        entity.setRuc(dto.getRuc());
        entity.setNombres(dto.getNombres());
        entity.setApellidos(dto.getApellidos());
        entity.setEmail(dto.getEmail());
        entity.setTelefono(dto.getTelefono());
        entity.setDepartamento(dto.getDepartamento());
        entity.setProvincia(dto.getProvincia());
        entity.setDistrito(dto.getDistrito());
        entity.setActivo(dto.getActivo());
    }
}
