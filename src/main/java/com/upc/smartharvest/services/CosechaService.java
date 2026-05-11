package com.upc.smartharvest.services;

import com.upc.smartharvest.DTOS.ReporteCosechaDTO;
import com.upc.smartharvest.entities.Cosecha;

import java.time.LocalDate;
import java.util.List;

public interface CosechaService {

    List<Cosecha> listar();

    Cosecha obtenerPorId(Long id);

    Cosecha registrar(Cosecha cosecha);

    Cosecha actualizar(Long id, Cosecha cosecha);

    void eliminar(Long id);

    List<Cosecha> listarPorCultivo(Long cultivoId);

    List<Cosecha> listarPorCultivoOrdenFechaDesc(Long cultivoId);

    List<Cosecha> listarPorFechaCosechaEntre(LocalDate inicio, LocalDate fin);

    List<Cosecha> listarPorParcela(Long parcelaId);

    public List<ReporteCosechaDTO> generarReporteCosecha(LocalDate fechaInicio, LocalDate fechaFin);

}
