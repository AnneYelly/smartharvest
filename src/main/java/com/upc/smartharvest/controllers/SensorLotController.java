package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.SensorLotDTO;
import com.upc.smartharvest.entities.*;
import com.upc.smartharvest.services.SensorLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upc.smartharvest.services.LecturaSensorService;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sensores")
@RequiredArgsConstructor
public class SensorLotController {

    private final SensorLotService sensorLotService;
    private final LecturaSensorService lecturaSensorService;

    @GetMapping
    public ResponseEntity<List<SensorLotDTO>> findAll() {
        return new ResponseEntity<>(sensorLotService.listar().stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/ultimas-lecturas")
    public ResponseEntity<List<Map<String, Object>>> listarSensoresConUltimaLectura() {
        List<Map<String, Object>> respuesta = sensorLotService.listar()
                .stream()
                .map(sensor -> {
                    Map<String, Object> item = new LinkedHashMap<>();

                    item.put("sensor", toDTO(sensor));

                    List<LecturaSensor> lecturas = lecturaSensorService.listarPorSensorOrdenFechaDesc(sensor.getId());

                    if (lecturas.isEmpty()) {
                        item.put("ultimaLectura", null);
                    } else {
                        LecturaSensor ultima = lecturas.get(0);

                        Map<String, Object> ultimaLectura = new LinkedHashMap<>();
                        ultimaLectura.put("id", ultima.getId());
                        ultimaLectura.put("valor", ultima.getValor());
                        ultimaLectura.put("unidadMedida", ultima.getUnidadMedida());
                        ultimaLectura.put("esAnomalia", ultima.getEsAnomalia());
                        ultimaLectura.put("fechaHora", ultima.getFechaHora());

                        item.put("ultimaLectura", ultimaLectura);
                    }

                    return item;
                })
                .toList();

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/baterias")
    public ResponseEntity<List<Map<String, Object>>> listarBateriasSensores() {
        List<Map<String, Object>> respuesta = sensorLotService.listar()
                .stream()
                .map(sensor -> {
                    Map<String, Object> item = new LinkedHashMap<>();

                    item.put("id", sensor.getId());
                    item.put("codigo", sensor.getCodigo());
                    item.put("tipoSensor", sensor.getTipoSensor());
                    item.put("estado", sensor.getEstado());
                    item.put("bateriaPorcentaje", sensor.getBateriaPorcentaje());
                    item.put("representacion", sensor.getBateriaPorcentaje() + "%");

                    return item;
                })
                .toList();

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}/bateria")
    public ResponseEntity<Map<String, Object>> obtenerBateriaSensor(@PathVariable Long id) {
        try {
            SensorLot sensor = sensorLotService.obtenerPorId(id);

            Map<String, Object> respuesta = new LinkedHashMap<>();
            respuesta.put("id", sensor.getId());
            respuesta.put("codigo", sensor.getCodigo());
            respuesta.put("tipoSensor", sensor.getTipoSensor());
            respuesta.put("estado", sensor.getEstado());
            respuesta.put("bateriaPorcentaje", sensor.getBateriaPorcentaje());
            respuesta.put("representacion", sensor.getBateriaPorcentaje() + "%");

            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/fechas-instalacion")
    public ResponseEntity<List<Map<String, Object>>> listarFechasInstalacionSensores() {
        List<Map<String, Object>> respuesta = sensorLotService.listar()
                .stream()
                .map(sensor -> {
                    Map<String, Object> item = new LinkedHashMap<>();

                    item.put("id", sensor.getId());
                    item.put("codigo", sensor.getCodigo());
                    item.put("tipoSensor", sensor.getTipoSensor());
                    item.put("estado", sensor.getEstado());
                    item.put("fechaInstalacion", sensor.getFechaInstalacion());

                    return item;
                })
                .toList();

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/{id}/fecha-instalacion")
    public ResponseEntity<Map<String, Object>> obtenerFechaInstalacionSensor(@PathVariable Long id) {
        try {
            SensorLot sensor = sensorLotService.obtenerPorId(id);

            Map<String, Object> respuesta = new LinkedHashMap<>();
            respuesta.put("id", sensor.getId());
            respuesta.put("codigo", sensor.getCodigo());
            respuesta.put("tipoSensor", sensor.getTipoSensor());
            respuesta.put("estado", sensor.getEstado());
            respuesta.put("fechaInstalacion", sensor.getFechaInstalacion());

            return new ResponseEntity<>(respuesta, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorLotDTO> findById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(toDTO(sensorLotService.obtenerPorId(id)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SensorLotDTO dto) {
        try {
            SensorLot entity = new SensorLot();
            copyToEntity(dto, entity);
            return new ResponseEntity<>(toDTO(sensorLotService.registrar(entity)), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorLotDTO> update(@PathVariable Long id, @RequestBody SensorLotDTO dto) {
        try {
            SensorLot entity = new SensorLot();
            copyToEntity(dto, entity);
            return new ResponseEntity<>(toDTO(sensorLotService.actualizar(id, entity)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            sensorLotService.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/parcela/{parcelaId}")
    public ResponseEntity<List<SensorLotDTO>> findParcelaParcelaId(@PathVariable Long parcelaId) {
        return new ResponseEntity<>(sensorLotService.listarPorParcela(parcelaId).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<SensorLotDTO> findCodigoCodigo(@PathVariable String codigo) {
        return sensorLotService.buscarPorCodigo(codigo)
                        .map(s -> new ResponseEntity<>(toDTO(s), HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/parcela/{parcelaId}/estado/{estado}")
    public ResponseEntity<List<SensorLotDTO>> findParcelaParcelaIdEstadoEstado(@PathVariable Long parcelaId, @PathVariable String estado) {
        return new ResponseEntity<>(sensorLotService.listarPorParcelaYEstado(parcelaId, estado).stream().map(this::toDTO).toList(), HttpStatus.OK);
    }

    private SensorLotDTO toDTO(SensorLot entity) {
        SensorLotDTO dto = new SensorLotDTO();
        dto.setId(entity.getId());
        dto.setParcelaId(entity.getParcela() != null ? entity.getParcela().getId() : null);
        dto.setCodigo(entity.getCodigo());
        dto.setTipoSensor(entity.getTipoSensor());
        dto.setUnidadMedida(entity.getUnidadMedida());
        dto.setEstado(entity.getEstado());
        dto.setUmbralMin(entity.getUmbralMin());
        dto.setUmbralMax(entity.getUmbralMax());
        dto.setBateriaPorcentaje(entity.getBateriaPorcentaje());
        dto.setUltimaLecturaAt(entity.getUltimaLecturaAt());
        dto.setFechaInstalacion(entity.getFechaInstalacion());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private void copyToEntity(SensorLotDTO dto, SensorLot entity) {
        if (dto.getParcelaId() != null) {
            Parcela relation = new Parcela();
            relation.setId(dto.getParcelaId());
            entity.setParcela(relation);
        } else {
            entity.setParcela(null);
        }
        entity.setCodigo(dto.getCodigo());
        entity.setTipoSensor(dto.getTipoSensor());
        entity.setUnidadMedida(dto.getUnidadMedida());
        entity.setEstado(dto.getEstado());
        entity.setUmbralMin(dto.getUmbralMin());
        entity.setUmbralMax(dto.getUmbralMax());
        entity.setBateriaPorcentaje(dto.getBateriaPorcentaje());
        entity.setUltimaLecturaAt(dto.getUltimaLecturaAt());
        entity.setFechaInstalacion(dto.getFechaInstalacion());
    }
}
