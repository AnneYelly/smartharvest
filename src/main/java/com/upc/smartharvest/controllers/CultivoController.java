package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.CultivoDTO;
import com.upc.smartharvest.entities.*;
import com.upc.smartharvest.services.CultivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cultivos")
@RequiredArgsConstructor
public class CultivoController {

    private final CultivoService cultivoService;

    @GetMapping
    public ResponseEntity<List<CultivoDTO>> findAll() {
        return new ResponseEntity<>(
                cultivoService.listar()
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CultivoDTO> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(
                    toDTO(cultivoService.obtenerPorId(id)),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<CultivoDTO> create(@RequestBody CultivoDTO dto) {
        Cultivo entity = new Cultivo();
        copyToEntity(dto, entity);

        return new ResponseEntity<>(
                toDTO(cultivoService.registrar(entity)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CultivoDTO> update(
            @PathVariable Long id,
            @RequestBody CultivoDTO dto) {
        try {
            Cultivo entity = new Cultivo();
            copyToEntity(dto, entity);

            return new ResponseEntity<>(
                    toDTO(cultivoService.actualizar(id, entity)),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        try {
            cultivoService.obtenerPorId(id);
            cultivoService.eliminar(id);

            return new ResponseEntity<>(
                    "Cultivo desactivado correctamente",
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @GetMapping("/parcela/{parcelaId}")
    public ResponseEntity<List<CultivoDTO>> findParcelaParcelaId(@PathVariable Long parcelaId) {
        return new ResponseEntity<>(
                cultivoService.listarPorParcela(parcelaId)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/parcela/{parcelaId}/estado/{estado}")
    public ResponseEntity<List<CultivoDTO>> findParcelaParcelaIdEstadoEstado(
            @PathVariable Long parcelaId,
            @PathVariable String estado) {
        return new ResponseEntity<>(
                cultivoService.listarPorParcelaYEstado(parcelaId, estado)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<CultivoDTO>> findEstadoEstado(@PathVariable String estado) {
        return new ResponseEntity<>(
                cultivoService.listarPorEstado(estado)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    private CultivoDTO toDTO(Cultivo entity) {
        CultivoDTO dto = new CultivoDTO();
        dto.setId(entity.getId());
        dto.setParcelaId(entity.getParcela() != null ? entity.getParcela().getId() : null);
        dto.setNombre(entity.getNombre());
        dto.setVariedad(entity.getVariedad());
        dto.setFechaSiembra(entity.getFechaSiembra());
        dto.setFechaCosechaEstimada(entity.getFechaCosechaEstimada());
        dto.setFechaCosechaReal(entity.getFechaCosechaReal());
        dto.setEstado(entity.getEstado());
        dto.setSuperficieHectareas(entity.getSuperficieHectareas());
        dto.setRendimientoEsperadoKg(entity.getRendimientoEsperadoKg());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private void copyToEntity(CultivoDTO dto, Cultivo entity) {
        if (dto.getParcelaId() != null) {
            Parcela relation = new Parcela();
            relation.setId(dto.getParcelaId());
            entity.setParcela(relation);
        } else {
            entity.setParcela(null);
        }

        entity.setNombre(dto.getNombre());
        entity.setVariedad(dto.getVariedad());
        entity.setFechaSiembra(dto.getFechaSiembra());
        entity.setFechaCosechaEstimada(dto.getFechaCosechaEstimada());
        entity.setFechaCosechaReal(dto.getFechaCosechaReal());
        entity.setEstado(dto.getEstado());
        entity.setSuperficieHectareas(dto.getSuperficieHectareas());
        entity.setRendimientoEsperadoKg(dto.getRendimientoEsperadoKg());
    }
}