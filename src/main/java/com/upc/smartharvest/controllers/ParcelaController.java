package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.ParcelaDTO;
import com.upc.smartharvest.entities.*;
import com.upc.smartharvest.services.ParcelaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/parcelas")
@RequiredArgsConstructor
public class ParcelaController {

    private final ParcelaService parcelaService;

    @GetMapping
    public ResponseEntity<List<ParcelaDTO>> findAll() {
        return new ResponseEntity<>(parcelaService.listar().stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParcelaDTO> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(toDTO(parcelaService.obtenerPorId(id)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ParcelaDTO> create(@RequestBody ParcelaDTO dto) {
        Parcela entity = new Parcela();
        copyToEntity(dto, entity);
        return new ResponseEntity<>(toDTO(parcelaService.registrar(entity)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParcelaDTO> update(@PathVariable Long id, @RequestBody ParcelaDTO dto) {
        try {
            Parcela entity = new Parcela();
            copyToEntity(dto, entity);
            return new ResponseEntity<>(toDTO(parcelaService.actualizar(id, entity)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            parcelaService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/agricultor/{agricultorId}")
    public ResponseEntity<List<ParcelaDTO>> findAgricultorAgricultorId(@PathVariable Long agricultorId) {
        return new ResponseEntity<>(parcelaService.listarPorAgricultor(agricultorId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/agricultor/{agricultorId}/activas")
    public ResponseEntity<List<ParcelaDTO>> findAgricultorAgricultorIdActivas(@PathVariable Long agricultorId) {
        return new ResponseEntity<>(parcelaService.listarActivasPorAgricultor(agricultorId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<ParcelaDTO>> findActivas() {
        return new ResponseEntity<>(parcelaService.listarActivas().stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    private ParcelaDTO toDTO(Parcela entity) {
        ParcelaDTO dto = new ParcelaDTO();
        dto.setId(entity.getId());
        dto.setAgricultorId(entity.getAgricultor() != null ? entity.getAgricultor().getId() : null);
        dto.setNombre(entity.getNombre());
        dto.setUbicacion(entity.getUbicacion());
        dto.setLatitud(entity.getLatitud());
        dto.setLongitud(entity.getLongitud());
        dto.setAltitudMsnm(entity.getAltitudMsnm());
        dto.setTamanoHectareas(entity.getTamanoHectareas());
        dto.setTipoSuelo(entity.getTipoSuelo());
        dto.setFuenteAgua(entity.getFuenteAgua());
        dto.setActivo(entity.getActivo());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private void copyToEntity(ParcelaDTO dto, Parcela entity) {
        if (dto.getAgricultorId() != null) {
            Agricultor relation = new Agricultor();
            relation.setId(dto.getAgricultorId());
            entity.setAgricultor(relation);
        } else {
            entity.setAgricultor(null);
        }
        entity.setNombre(dto.getNombre());
        entity.setUbicacion(dto.getUbicacion());
        entity.setLatitud(dto.getLatitud());
        entity.setLongitud(dto.getLongitud());
        entity.setAltitudMsnm(dto.getAltitudMsnm());
        entity.setTamanoHectareas(dto.getTamanoHectareas());
        entity.setTipoSuelo(dto.getTipoSuelo());
        entity.setFuenteAgua(dto.getFuenteAgua());
        entity.setActivo(dto.getActivo());
    }
}
