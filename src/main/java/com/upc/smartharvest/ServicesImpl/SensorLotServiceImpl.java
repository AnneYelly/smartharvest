package com.upc.smartharvest.ServicesImpl;

import com.upc.smartharvest.entities.SensorLot;
import com.upc.smartharvest.repository.SensorLotRepository;
import com.upc.smartharvest.services.SensorLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorLotServiceImpl implements SensorLotService {

    private final SensorLotRepository sensorLotRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SensorLot> listar() {
        return sensorLotRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public SensorLot obtenerPorId(Long id) {
        return sensorLotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public SensorLot registrar(SensorLot sensorLot) {
        if (sensorLotRepository.existsByCodigo(sensorLot.getCodigo())) {
            throw new RuntimeException("Ya existe un sensor con el código: " + sensorLot.getCodigo());
        }
        return sensorLotRepository.save(sensorLot);
    }

    @Override
    @Transactional
    public SensorLot actualizar(Long id, SensorLot sensorLot) {
        SensorLot actual = obtenerPorId(id);

        if (!Objects.equals(actual.getCodigo(), sensorLot.getCodigo()) && sensorLotRepository.existsByCodigo(sensorLot.getCodigo())) {
            throw new RuntimeException("Ya existe un sensor con el código: " + sensorLot.getCodigo());
        }

        actual.setParcela(sensorLot.getParcela());
        actual.setCodigo(sensorLot.getCodigo());
        actual.setTipoSensor(sensorLot.getTipoSensor());
        actual.setUnidadMedida(sensorLot.getUnidadMedida());
        actual.setEstado(sensorLot.getEstado());
        actual.setUmbralMin(sensorLot.getUmbralMin());
        actual.setUmbralMax(sensorLot.getUmbralMax());
        actual.setBateriaPorcentaje(sensorLot.getBateriaPorcentaje());
        actual.setUltimaLecturaAt(sensorLot.getUltimaLecturaAt());
        actual.setFechaInstalacion(sensorLot.getFechaInstalacion());

        return sensorLotRepository.save(actual);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        SensorLot sensorLot = obtenerPorId(id);
        sensorLotRepository.delete(sensorLot);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SensorLot> listarPorParcela(Long parcelaId) {
        return sensorLotRepository.findByParcelaId(parcelaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SensorLot> buscarPorCodigo(String codigo) {
        return sensorLotRepository.findByCodigo(codigo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SensorLot> listarPorParcelaYEstado(Long parcelaId, String estado) {
        return sensorLotRepository.findByParcelaIdAndEstado(parcelaId, estado);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(String codigo) {
        return sensorLotRepository.existsByCodigo(codigo);
    }
}
