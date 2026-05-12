package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.CosechaDTO;
import com.upc.smartharvest.DTOS.ReporteCosechaDTO;
import com.upc.smartharvest.entities.*;
import com.upc.smartharvest.services.CosechaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/cosechas")
@RequiredArgsConstructor
public class CosechaController {

    private final CosechaService cosechaService;

    @GetMapping
    public ResponseEntity<List<CosechaDTO>> findAll() {
        return new ResponseEntity<>(
                cosechaService.listar()
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CosechaDTO> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(
                    toDTO(cosechaService.obtenerPorId(id)),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<CosechaDTO> create(@RequestBody CosechaDTO dto) {
        Cosecha entity = new Cosecha();
        copyToEntity(dto, entity);

        return new ResponseEntity<>(
                toDTO(cosechaService.registrar(entity)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CosechaDTO> update(
            @PathVariable Long id,
            @RequestBody CosechaDTO dto) {
        try {
            Cosecha entity = new Cosecha();
            copyToEntity(dto, entity);

            return new ResponseEntity<>(
                    toDTO(cosechaService.actualizar(id, entity)),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            cosechaService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @GetMapping("/cultivo/{cultivoId}")
    public ResponseEntity<List<CosechaDTO>> findCultivoCultivoId(@PathVariable Long cultivoId) {
        return new ResponseEntity<>(
                cosechaService.listarPorCultivo(cultivoId)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/cultivo/{cultivoId}/recientes")
    public ResponseEntity<List<CosechaDTO>> findCultivoCultivoIdRecientes(@PathVariable Long cultivoId) {
        return new ResponseEntity<>(
                cosechaService.listarPorCultivoOrdenFechaDesc(cultivoId)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/rango")
    public ResponseEntity<List<CosechaDTO>> findRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return new ResponseEntity<>(
                cosechaService.listarPorFechaCosechaEntre(inicio, fin)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/parcela/{parcelaId}")
    public ResponseEntity<List<CosechaDTO>> findParcelaParcelaId(@PathVariable Long parcelaId) {
        return new ResponseEntity<>(
                cosechaService.listarPorParcela(parcelaId)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @PostMapping("/reporte")
    public ResponseEntity<List<ReporteCosechaDTO>> obtenerReporte(
            @RequestBody ReporteCosechaDTO.Request fechas) {
        return new ResponseEntity<>(
                cosechaService.generarReporteCosecha(
                        fechas.getFechaInicio(),
                        fechas.getFechaFin()
                ),
                HttpStatus.OK
        );
    }

    private CosechaDTO toDTO(Cosecha entity) {
        CosechaDTO dto = new CosechaDTO();
        dto.setId(entity.getId());
        dto.setCultivoId(entity.getCultivo() != null ? entity.getCultivo().getId() : null);
        dto.setFechaCosecha(entity.getFechaCosecha());
        dto.setCantidadKg(entity.getCantidadKg());
        dto.setCantidadDescarteKg(entity.getCantidadDescarteKg());
        dto.setCalidad(entity.getCalidad());
        dto.setMetodoCosecha(entity.getMetodoCosecha());
        dto.setPrecioVentaKg(entity.getPrecioVentaKg());
        dto.setDestino(entity.getDestino());
        dto.setObservaciones(entity.getObservaciones());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private void copyToEntity(CosechaDTO dto, Cosecha entity) {
        if (dto.getCultivoId() != null) {
            Cultivo relation = new Cultivo();
            relation.setId(dto.getCultivoId());
            entity.setCultivo(relation);
        } else {
            entity.setCultivo(null);
        }

        entity.setFechaCosecha(dto.getFechaCosecha());
        entity.setCantidadKg(dto.getCantidadKg());
        entity.setCantidadDescarteKg(dto.getCantidadDescarteKg());
        entity.setCalidad(dto.getCalidad());
        entity.setMetodoCosecha(dto.getMetodoCosecha());
        entity.setPrecioVentaKg(dto.getPrecioVentaKg());
        entity.setDestino(dto.getDestino());
        entity.setObservaciones(dto.getObservaciones());
    }
}