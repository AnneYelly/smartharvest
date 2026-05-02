package com.upc.smartharvest.services;

import com.upc.smartharvest.entities.Cultivo;

import java.util.List;

public interface CultivoService {

    List<Cultivo> listar();

    Cultivo obtenerPorId(Long id);

    Cultivo registrar(Cultivo cultivo);

    Cultivo actualizar(Long id, Cultivo cultivo);

    void eliminar(Long id);

    List<Cultivo> listarPorParcela(Long parcelaId);

    List<Cultivo> listarPorParcelaYEstado(Long parcelaId, String estado);

    List<Cultivo> listarPorEstado(String estado);
}
