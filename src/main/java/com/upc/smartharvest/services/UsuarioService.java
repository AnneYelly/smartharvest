package com.upc.smartharvest.services;

import com.upc.smartharvest.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listar();

    Usuario obtenerPorId(Long id);

    Usuario registrar(Usuario usuario);

    Usuario actualizar(Long id, Usuario usuario);

    void eliminar(Long id);

    Optional<Usuario> buscarPorUsername(String username);

    List<Usuario> listarPorAgricultor(Long agricultorId);

    List<Usuario> listarPorEstado(String estado);

    boolean existePorUsername(String username);
}
