package com.upc.smartharvest.services;

import com.upc.smartharvest.entities.DatoClimatico;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DatoClimaticoService {

    List<DatoClimatico> listar();

    DatoClimatico obtenerPorId(Long id);

    DatoClimatico registrar(DatoClimatico datoClimatico);

    DatoClimatico actualizar(Long id, DatoClimatico datoClimatico);

    void eliminar(Long id);

    List<DatoClimatico> listarPorParcela(Long parcelaId);

    List<DatoClimatico> listarPorParcelaYFechaEntre(Long parcelaId, LocalDate inicio, LocalDate fin);

    Optional<DatoClimatico> buscarPorParcelaYFecha(Long parcelaId, LocalDate fecha);

    List<DatoClimatico> listarPorParcelaOrdenFechaDesc(Long parcelaId);
}
