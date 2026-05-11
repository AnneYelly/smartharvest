package com.upc.smartharvest.ServicesImpl;

import com.upc.smartharvest.entities.LecturaSensor;
import com.upc.smartharvest.repository.LecturaSensorRepository;
import com.upc.smartharvest.services.LecturaSensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LecturaSensorServiceImpl implements LecturaSensorService {

    private final LecturaSensorRepository lecturaSensorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<LecturaSensor> listar() {
        return lecturaSensorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public LecturaSensor obtenerPorId(Long id) {
        return lecturaSensorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lectura de sensor no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public LecturaSensor registrar(LecturaSensor lecturaSensor) {
        return lecturaSensorRepository.save(lecturaSensor);
    }

    @Override
    @Transactional
    public LecturaSensor actualizar(Long id, LecturaSensor lecturaSensor) {
        LecturaSensor actual = obtenerPorId(id);

        actual.setSensor(lecturaSensor.getSensor());
        actual.setValor(lecturaSensor.getValor());
        actual.setUnidadMedida(lecturaSensor.getUnidadMedida());
        actual.setEsAnomalia(lecturaSensor.getEsAnomalia());
        actual.setFechaHora(lecturaSensor.getFechaHora());

        return lecturaSensorRepository.save(actual);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        LecturaSensor lecturaSensor = obtenerPorId(id);
        lecturaSensorRepository.delete(lecturaSensor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LecturaSensor> listarPorSensor(Long sensorId) {
        return lecturaSensorRepository.findBySensorId(sensorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LecturaSensor> listarPorSensorOrdenFechaDesc(Long sensorId) {
        return lecturaSensorRepository.findBySensorIdOrderByFechaHoraDesc(sensorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LecturaSensor> listarPorSensorYFechaHoraEntre(Long sensorId, LocalDateTime inicio, LocalDateTime fin) {
        return lecturaSensorRepository.findBySensorIdAndFechaHoraBetween(sensorId, inicio, fin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LecturaSensor> listarAnomalias() {
        return lecturaSensorRepository.findByEsAnomaliaTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LecturaSensor> listarAnomaliasPorSensor(Long sensorId) {
        return lecturaSensorRepository.findBySensorIdAndEsAnomaliaTrue(sensorId);
    }

    @Override
    public LecturaSensor obtenerUltimaLecturaPorSensor(Long sensorId) {
        return lecturaSensorRepository.findBySensorIdOrderByFechaHoraDesc(sensorId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No existen lecturas para el sensor con ID: " + sensorId));
    }
}
