package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.DatoClimaticoDTO;
import com.upc.smartharvest.entities.*;
import com.upc.smartharvest.services.DatoClimaticoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/datos-climaticos")
@RequiredArgsConstructor
public class DatoClimaticoController {

    private final DatoClimaticoService datoClimaticoService;

    @GetMapping
    public ResponseEntity<List<DatoClimaticoDTO>> findAll() {
        return new ResponseEntity<>(datoClimaticoService.listar().stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatoClimaticoDTO> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(toDTO(datoClimaticoService.obtenerPorId(id)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<DatoClimaticoDTO> create(@RequestBody DatoClimaticoDTO dto) {
        DatoClimatico entity = new DatoClimatico();
        copyToEntity(dto, entity);
        return new ResponseEntity<>(toDTO(datoClimaticoService.registrar(entity)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatoClimaticoDTO> update(@PathVariable Long id, @RequestBody DatoClimaticoDTO dto) {
        try {
            DatoClimatico entity = new DatoClimatico();
            copyToEntity(dto, entity);
            return new ResponseEntity<>(toDTO(datoClimaticoService.actualizar(id, entity)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            datoClimaticoService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/parcela/{parcelaId}")
    public ResponseEntity<List<DatoClimaticoDTO>> findParcelaParcelaId(@PathVariable Long parcelaId) {
        return new ResponseEntity<>(datoClimaticoService.listarPorParcela(parcelaId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/parcela/{parcelaId}/rango")
    public ResponseEntity<List<DatoClimaticoDTO>> findParcelaParcelaIdRango(@PathVariable Long parcelaId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return new ResponseEntity<>(datoClimaticoService.listarPorParcelaYFechaEntre(parcelaId, inicio, fin).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/parcela/{parcelaId}/fecha/{fecha}")
    public ResponseEntity<DatoClimaticoDTO> findParcelaParcelaIdFechaFecha(@PathVariable Long parcelaId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return datoClimaticoService.buscarPorParcelaYFecha(parcelaId, fecha)
                        .map(d -> new ResponseEntity<>(toDTO(d), HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/parcela/{parcelaId}/recientes")
    public ResponseEntity<List<DatoClimaticoDTO>> findParcelaParcelaIdRecientes(@PathVariable Long parcelaId) {
        return new ResponseEntity<>(datoClimaticoService.listarPorParcelaOrdenFechaDesc(parcelaId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    private DatoClimaticoDTO toDTO(DatoClimatico entity) {
        DatoClimaticoDTO dto = new DatoClimaticoDTO();
        dto.setId(entity.getId());
        dto.setParcelaId(entity.getParcela() != null ? entity.getParcela().getId() : null);
        dto.setFecha(entity.getFecha());
        dto.setFuente(entity.getFuente());
        dto.setTemperaturaMin(entity.getTemperaturaMin());
        dto.setTemperaturaMax(entity.getTemperaturaMax());
        dto.setHumedadPorcentaje(entity.getHumedadPorcentaje());
        dto.setPrecipitacionMm(entity.getPrecipitacionMm());
        dto.setVelocidadVientoKmh(entity.getVelocidadVientoKmh());
        dto.setIndiceUv(entity.getIndiceUv());
        dto.setCondicion(entity.getCondicion());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private void copyToEntity(DatoClimaticoDTO dto, DatoClimatico entity) {
        if (dto.getParcelaId() != null) {
            Parcela relation = new Parcela();
            relation.setId(dto.getParcelaId());
            entity.setParcela(relation);
        } else {
            entity.setParcela(null);
        }
        entity.setFecha(dto.getFecha());
        entity.setFuente(dto.getFuente());
        entity.setTemperaturaMin(dto.getTemperaturaMin());
        entity.setTemperaturaMax(dto.getTemperaturaMax());
        entity.setHumedadPorcentaje(dto.getHumedadPorcentaje());
        entity.setPrecipitacionMm(dto.getPrecipitacionMm());
        entity.setVelocidadVientoKmh(dto.getVelocidadVientoKmh());
        entity.setIndiceUv(dto.getIndiceUv());
        entity.setCondicion(dto.getCondicion());
    }
}
