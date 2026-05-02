package com.upc.smartharvest.ServicesImpl;

import com.upc.smartharvest.entities.Usuario;
import com.upc.smartharvest.repository.UsuarioRepository;
import com.upc.smartharvest.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("Ya existe un usuario con username: " + usuario.getUsername());
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario actualizar(Long id, Usuario usuario) {
        Usuario actual = obtenerPorId(id);

        if (!Objects.equals(actual.getUsername(), usuario.getUsername()) && usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("Ya existe un usuario con username: " + usuario.getUsername());
        }

        actual.setAgricultor(usuario.getAgricultor());
        actual.setUsername(usuario.getUsername());
        actual.setPasswordHash(usuario.getPasswordHash());
        actual.setRol(usuario.getRol());
        actual.setEstado(usuario.getEstado());
        actual.setUltimaAcceso(usuario.getUltimaAcceso());

        return usuarioRepository.save(actual);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Usuario usuario = obtenerPorId(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorAgricultor(Long agricultorId) {
        return usuarioRepository.findByAgricultorId(agricultorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorEstado(String estado) {
        return usuarioRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
}
