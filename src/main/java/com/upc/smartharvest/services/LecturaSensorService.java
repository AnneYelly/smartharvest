package com.upc.smartharvest.services;

import com.upc.smartharvest.entities.LecturaSensor;

import java.time.LocalDateTime;
import java.util.List;

public interface LecturaSensorService {

    List<LecturaSensor> listar();

    LecturaSensor obtenerPorId(Long id);

    LecturaSensor registrar(LecturaSensor lecturaSensor);

    LecturaSensor actualizar(Long id, LecturaSensor lecturaSensor);

    void eliminar(Long id);

    List<LecturaSensor> listarPorSensor(Long sensorId);

    List<LecturaSensor> listarPorSensorOrdenFechaDesc(Long sensorId);

    List<LecturaSensor> listarPorSensorYFechaHoraEntre(Long sensorId, LocalDateTime inicio, LocalDateTime fin);

    List<LecturaSensor> listarAnomalias();

    List<LecturaSensor> listarAnomaliasPorSensor(Long sensorId);
}
