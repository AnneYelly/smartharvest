package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.AlertaDTO;
import com.upc.smartharvest.entities.*;
import com.upc.smartharvest.services.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    public ResponseEntity<List<AlertaDTO>> findAll() {
        return new ResponseEntity<>(
                alertaService.listar()
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/notificaciones")
    public ResponseEntity<List<AlertaDTO>> listarNotificacionesPendientes() {
        return new ResponseEntity<>(
                alertaService.listarNotificacionesPendientes()
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/notificaciones/pendientes/count")
    public ResponseEntity<Long> contarNotificacionesPendientes() {
        return new ResponseEntity<>(
                alertaService.contarNotificacionesPendientes(),
                HttpStatus.OK
        );
    }

    @GetMapping("/notificaciones/importantes")
    public ResponseEntity<List<AlertaDTO>> listarAlertasImportantes() {
        return new ResponseEntity<>(
                alertaService.listarAlertasImportantes()
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}/vista")
    public ResponseEntity<AlertaDTO> marcarComoVista(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(
                    toDTO(alertaService.marcarComoVista(id)),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaDTO> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(
                    toDTO(alertaService.obtenerPorId(id)),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<AlertaDTO> create(@RequestBody AlertaDTO dto) {
        Alerta entity = new Alerta();
        copyToEntity(dto, entity);

        return new ResponseEntity<>(
                toDTO(alertaService.registrar(entity)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaDTO> update(
            @PathVariable Long id,
            @RequestBody AlertaDTO dto) {
        try {
            Alerta entity = new Alerta();
            copyToEntity(dto, entity);

            return new ResponseEntity<>(
                    toDTO(alertaService.actualizar(id, entity)),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            alertaService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @GetMapping("/parcela/{parcelaId}")
    public ResponseEntity<List<AlertaDTO>> findParcelaParcelaId(@PathVariable Long parcelaId) {
        return new ResponseEntity<>(
                alertaService.listarPorParcela(parcelaId)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AlertaDTO>> findEstadoEstado(@PathVariable String estado) {
        return new ResponseEntity<>(
                alertaService.listarPorEstado(estado)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/parcela/{parcelaId}/estado/{estado}")
    public ResponseEntity<List<AlertaDTO>> findParcelaParcelaIdEstadoEstado(
            @PathVariable Long parcelaId,
            @PathVariable String estado) {
        return new ResponseEntity<>(
                alertaService.listarPorParcelaYEstado(parcelaId, estado)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<AlertaDTO>> findNivelNivel(@PathVariable String nivel) {
        return new ResponseEntity<>(
                alertaService.listarPorNivel(nivel)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/parcela/{parcelaId}/recientes")
    public ResponseEntity<List<AlertaDTO>> findParcelaParcelaIdRecientes(@PathVariable Long parcelaId) {
        return new ResponseEntity<>(
                alertaService.listarPorParcelaOrdenFechaDesc(parcelaId)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/nivel/{nivel}/estado/{estado}")
    public ResponseEntity<List<AlertaDTO>> findNivelNivelEstadoEstado(
            @PathVariable String nivel,
            @PathVariable String estado) {
        return new ResponseEntity<>(
                alertaService.listarPorNivelYEstado(nivel, estado)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    private AlertaDTO toDTO(Alerta entity) {
        AlertaDTO dto = new AlertaDTO();
        dto.setId(entity.getId());
        dto.setParcelaId(entity.getParcela() != null ? entity.getParcela().getId() : null);
        dto.setLecturaId(entity.getLectura() != null ? entity.getLectura().getId() : null);
        dto.setTipoAlerta(entity.getTipoAlerta());
        dto.setNivel(entity.getNivel());
        dto.setMensaje(entity.getMensaje());
        dto.setEstado(entity.getEstado());
        dto.setUsuarioResolverId(entity.getUsuarioResolver() != null ? entity.getUsuarioResolver().getId() : null);
        dto.setFechaResolucion(entity.getFechaResolucion());
        dto.setFechaHora(entity.getFechaHora());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private void copyToEntity(AlertaDTO dto, Alerta entity) {
        if (dto.getParcelaId() != null) {
            Parcela relation = new Parcela();
            relation.setId(dto.getParcelaId());
            entity.setParcela(relation);
        } else {
            entity.setParcela(null);
        }

        if (dto.getLecturaId() != null) {
            LecturaSensor relation = new LecturaSensor();
            relation.setId(dto.getLecturaId());
            entity.setLectura(relation);
        } else {
            entity.setLectura(null);
        }

        entity.setTipoAlerta(dto.getTipoAlerta());
        entity.setNivel(dto.getNivel());
        entity.setMensaje(dto.getMensaje());
        entity.setEstado(dto.getEstado());

        if (dto.getUsuarioResolverId() != null) {
            Usuario relation = new Usuario();
            relation.setId(dto.getUsuarioResolverId());
            entity.setUsuarioResolver(relation);
        } else {
            entity.setUsuarioResolver(null);
        }

        entity.setFechaResolucion(dto.getFechaResolucion());
        entity.setFechaHora(dto.getFechaHora());
    }
}