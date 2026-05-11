package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.TareaAgricolaDTO;
import com.upc.smartharvest.entities.*;
import com.upc.smartharvest.services.TareaAgricolaService;
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
@RequestMapping("/api/tareas-agricolas")
@RequiredArgsConstructor
public class TareaAgricolaController {

    private final TareaAgricolaService tareaAgricolaService;

    @GetMapping
    public ResponseEntity<List<TareaAgricolaDTO>> findAll() {
        return new ResponseEntity<>(tareaAgricolaService.listar().stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaAgricolaDTO> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(toDTO(tareaAgricolaService.obtenerPorId(id)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<TareaAgricolaDTO>> filtrarTareas(
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        return new ResponseEntity<>(
                tareaAgricolaService.filtrarTareas(busqueda, estado, fechaInicio, fechaFin)
                        .stream()
                        .map(this::toDTO)
                        .toList(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<TareaAgricolaDTO> create(@RequestBody TareaAgricolaDTO dto) {
        TareaAgricola entity = new TareaAgricola();
        copyToEntity(dto, entity);
        return new ResponseEntity<>(toDTO(tareaAgricolaService.registrar(entity)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaAgricolaDTO> update(@PathVariable Long id, @RequestBody TareaAgricolaDTO dto) {
        try {
            TareaAgricola entity = new TareaAgricola();
            copyToEntity(dto, entity);
            return new ResponseEntity<>(toDTO(tareaAgricolaService.actualizar(id, entity)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            tareaAgricolaService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/parcela/{parcelaId}")
    public ResponseEntity<List<TareaAgricolaDTO>> findParcelaParcelaId(@PathVariable Long parcelaId) {
        return new ResponseEntity<>(tareaAgricolaService.listarPorParcela(parcelaId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/cultivo/{cultivoId}")
    public ResponseEntity<List<TareaAgricolaDTO>> findCultivoCultivoId(@PathVariable Long cultivoId) {
        return new ResponseEntity<>(tareaAgricolaService.listarPorCultivo(cultivoId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<TareaAgricolaDTO>> findUsuarioUsuarioId(@PathVariable Long usuarioId) {
        return new ResponseEntity<>(tareaAgricolaService.listarPorUsuario(usuarioId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/parcela/{parcelaId}/estado/{estado}")
    public ResponseEntity<List<TareaAgricolaDTO>> findParcelaParcelaIdEstadoEstado(@PathVariable Long parcelaId, @PathVariable String estado) {
        return new ResponseEntity<>(tareaAgricolaService.listarPorParcelaYEstado(parcelaId, estado).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}/estado/{estado}")
    public ResponseEntity<List<TareaAgricolaDTO>> findUsuarioUsuarioIdEstadoEstado(@PathVariable Long usuarioId, @PathVariable String estado) {
        return new ResponseEntity<>(tareaAgricolaService.listarPorUsuarioYEstado(usuarioId, estado).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/rango")
    public ResponseEntity<List<TareaAgricolaDTO>> findRango(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return new ResponseEntity<>(tareaAgricolaService.listarPorFechaProgramadaEntre(inicio, fin).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    private TareaAgricolaDTO toDTO(TareaAgricola entity) {
        TareaAgricolaDTO dto = new TareaAgricolaDTO();
        dto.setId(entity.getId());
        dto.setParcelaId(entity.getParcela() != null ? entity.getParcela().getId() : null);
        dto.setCultivoId(entity.getCultivo() != null ? entity.getCultivo().getId() : null);
        dto.setUsuarioId(entity.getUsuario() != null ? entity.getUsuario().getId() : null);
        dto.setTipoTarea(entity.getTipoTarea());
        dto.setDescripcion(entity.getDescripcion());
        dto.setFechaProgramada(entity.getFechaProgramada());
        dto.setFechaEjecucion(entity.getFechaEjecucion());
        dto.setEstado(entity.getEstado());
        dto.setPrioridad(entity.getPrioridad());
        dto.setCostoEstimado(entity.getCostoEstimado());
        dto.setCostoReal(entity.getCostoReal());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private void copyToEntity(TareaAgricolaDTO dto, TareaAgricola entity) {
        if (dto.getParcelaId() != null) {
            Parcela relation = new Parcela();
            relation.setId(dto.getParcelaId());
            entity.setParcela(relation);
        } else {
            entity.setParcela(null);
        }
        if (dto.getCultivoId() != null) {
            Cultivo relation = new Cultivo();
            relation.setId(dto.getCultivoId());
            entity.setCultivo(relation);
        } else {
            entity.setCultivo(null);
        }
        if (dto.getUsuarioId() != null) {
            Usuario relation = new Usuario();
            relation.setId(dto.getUsuarioId());
            entity.setUsuario(relation);
        } else {
            entity.setUsuario(null);
        }
        entity.setTipoTarea(dto.getTipoTarea());
        entity.setDescripcion(dto.getDescripcion());
        entity.setFechaProgramada(dto.getFechaProgramada());
        entity.setFechaEjecucion(dto.getFechaEjecucion());
        entity.setEstado(dto.getEstado());
        entity.setPrioridad(dto.getPrioridad());
        entity.setCostoEstimado(dto.getCostoEstimado());
        entity.setCostoReal(dto.getCostoReal());
    }
}
