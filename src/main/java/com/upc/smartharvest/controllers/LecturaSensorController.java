package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.LecturaSensorDTO;
import com.upc.smartharvest.entities.*;
import com.upc.smartharvest.services.LecturaSensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/lecturas-sensor")
@RequiredArgsConstructor
public class LecturaSensorController {

    private final LecturaSensorService lecturaSensorService;

    @GetMapping
    public ResponseEntity<List<LecturaSensorDTO>> findAll() {
        return new ResponseEntity<>(lecturaSensorService.listar().stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LecturaSensorDTO> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(toDTO(lecturaSensorService.obtenerPorId(id)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<LecturaSensorDTO> create(@RequestBody LecturaSensorDTO dto) {
        LecturaSensor entity = new LecturaSensor();
        copyToEntity(dto, entity);
        return new ResponseEntity<>(toDTO(lecturaSensorService.registrar(entity)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LecturaSensorDTO> update(@PathVariable Long id, @RequestBody LecturaSensorDTO dto) {
        try {
            LecturaSensor entity = new LecturaSensor();
            copyToEntity(dto, entity);
            return new ResponseEntity<>(toDTO(lecturaSensorService.actualizar(id, entity)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            lecturaSensorService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/sensor/{sensorId}")
    public ResponseEntity<List<LecturaSensorDTO>> findSensorSensorId(@PathVariable Long sensorId) {
        return new ResponseEntity<>(lecturaSensorService.listarPorSensor(sensorId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/sensor/{sensorId}/recientes")
    public ResponseEntity<List<LecturaSensorDTO>> findSensorSensorIdRecientes(@PathVariable Long sensorId) {
        return new ResponseEntity<>(lecturaSensorService.listarPorSensorOrdenFechaDesc(sensorId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/sensor/{sensorId}/rango")
    public ResponseEntity<List<LecturaSensorDTO>> findSensorSensorIdRango(@PathVariable Long sensorId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return new ResponseEntity<>(lecturaSensorService.listarPorSensorYFechaHoraEntre(sensorId, inicio, fin).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/anomalias")
    public ResponseEntity<List<LecturaSensorDTO>> findAnomalias() {
        return new ResponseEntity<>(lecturaSensorService.listarAnomalias().stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/sensor/{sensorId}/anomalias")
    public ResponseEntity<List<LecturaSensorDTO>> findSensorSensorIdAnomalias(@PathVariable Long sensorId) {
        return new ResponseEntity<>(lecturaSensorService.listarAnomaliasPorSensor(sensorId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/sensor/{sensorId}/ultima")
    public ResponseEntity<LecturaSensorDTO> obtenerUltimaLecturaPorSensor(@PathVariable Long sensorId) {
        try {
            return new ResponseEntity<>(
                    toDTO(lecturaSensorService.obtenerUltimaLecturaPorSensor(sensorId)),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private LecturaSensorDTO toDTO(LecturaSensor entity) {
        LecturaSensorDTO dto = new LecturaSensorDTO();
        dto.setId(entity.getId());
        dto.setSensorId(entity.getSensor() != null ? entity.getSensor().getId() : null);
        dto.setValor(entity.getValor());
        dto.setUnidadMedida(entity.getUnidadMedida());
        dto.setEsAnomalia(entity.getEsAnomalia());
        dto.setFechaHora(entity.getFechaHora());
        return dto;
    }

    private void copyToEntity(LecturaSensorDTO dto, LecturaSensor entity) {
        if (dto.getSensorId() != null) {
            SensorLot relation = new SensorLot();
            relation.setId(dto.getSensorId());
            entity.setSensor(relation);
        } else {
            entity.setSensor(null);
        }
        entity.setValor(dto.getValor());
        entity.setUnidadMedida(dto.getUnidadMedida());
        entity.setEsAnomalia(dto.getEsAnomalia());
        entity.setFechaHora(dto.getFechaHora());
    }
}
