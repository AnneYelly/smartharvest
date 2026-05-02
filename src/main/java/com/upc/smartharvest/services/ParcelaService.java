package com.upc.smartharvest.services;

import com.upc.smartharvest.entities.Parcela;

import java.util.List;

public interface ParcelaService {

    List<Parcela> listar();

    Parcela obtenerPorId(Long id);

    Parcela registrar(Parcela parcela);

    Parcela actualizar(Long id, Parcela parcela);

    void eliminar(Long id);

    List<Parcela> listarPorAgricultor(Long agricultorId);

    List<Parcela> listarActivasPorAgricultor(Long agricultorId);

    List<Parcela> listarActivas();
}
