package com.upc.smartharvest.services;

import com.upc.smartharvest.entities.SensorLot;

import java.util.List;
import java.util.Optional;

public interface SensorLotService {

    List<SensorLot> listar();

    SensorLot obtenerPorId(Long id);

    SensorLot registrar(SensorLot sensorLot);

    SensorLot actualizar(Long id, SensorLot sensorLot);

    void eliminar(Long id);

    List<SensorLot> listarPorParcela(Long parcelaId);

    Optional<SensorLot> buscarPorCodigo(String codigo);

    List<SensorLot> listarPorParcelaYEstado(Long parcelaId, String estado);

    boolean existePorCodigo(String codigo);

}
