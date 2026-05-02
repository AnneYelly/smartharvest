package com.upc.smartharvest.services;

import com.upc.smartharvest.entities.TareaAgricola;

import java.time.LocalDate;
import java.util.List;

public interface TareaAgricolaService {

    List<TareaAgricola> listar();

    TareaAgricola obtenerPorId(Long id);

    TareaAgricola registrar(TareaAgricola tareaAgricola);

    TareaAgricola actualizar(Long id, TareaAgricola tareaAgricola);

    void eliminar(Long id);

    List<TareaAgricola> listarPorParcela(Long parcelaId);

    List<TareaAgricola> listarPorCultivo(Long cultivoId);

    List<TareaAgricola> listarPorUsuario(Long usuarioId);

    List<TareaAgricola> listarPorParcelaYEstado(Long parcelaId, String estado);

    List<TareaAgricola> listarPorUsuarioYEstado(Long usuarioId, String estado);

    List<TareaAgricola> listarPorFechaProgramadaEntre(LocalDate inicio, LocalDate fin);
}
