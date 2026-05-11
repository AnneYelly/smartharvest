package com.upc.smartharvest.services;

import com.upc.smartharvest.entities.Agricultor;

import java.util.List;
import java.util.Optional;

public interface AgricultorService {

    List<Agricultor> listar();

    Agricultor obtenerPorId(Long id);

    Agricultor registrar(Agricultor agricultor);

    Agricultor actualizar(Long id, Agricultor agricultor);

    void eliminar(Long id);

    Optional<Agricultor> buscarPorDni(String dni);

    Optional<Agricultor> buscarPorEmail(String email);

    List<Agricultor> listarActivos();

    boolean existePorDni(String dni);

    boolean existePorEmail(String email);

    Agricultor actualizarPerfil(Long id, Agricultor agricultor);
}
