package com.upc.smartharvest.ServicesImpl;

import com.upc.smartharvest.entities.Agricultor;
import com.upc.smartharvest.repository.AgricultorRepository;
import com.upc.smartharvest.services.AgricultorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgricultorServiceImpl implements AgricultorService {

    private final AgricultorRepository agricultorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Agricultor> listar() {
        return agricultorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Agricultor obtenerPorId(Long id) {
        return agricultorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agricultor no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Agricultor registrar(Agricultor agricultor) {
        validarDniYEmailUnicos(agricultor);
        return agricultorRepository.save(agricultor);
    }

    @Override
    @Transactional
    public Agricultor actualizar(Long id, Agricultor agricultor) {
        Agricultor actual = obtenerPorId(id);

        if (!Objects.equals(actual.getDni(), agricultor.getDni()) && agricultorRepository.existsByDni(agricultor.getDni())) {
            throw new RuntimeException("Ya existe un agricultor con el DNI: " + agricultor.getDni());
        }

        if (agricultor.getEmail() != null
                && !Objects.equals(actual.getEmail(), agricultor.getEmail())
                && agricultorRepository.existsByEmail(agricultor.getEmail())) {
            throw new RuntimeException("Ya existe un agricultor con el email: " + agricultor.getEmail());
        }

        actual.setDni(agricultor.getDni());
        actual.setRuc(agricultor.getRuc());
        actual.setNombres(agricultor.getNombres());
        actual.setApellidos(agricultor.getApellidos());
        actual.setEmail(agricultor.getEmail());
        actual.setTelefono(agricultor.getTelefono());
        actual.setDepartamento(agricultor.getDepartamento());
        actual.setProvincia(agricultor.getProvincia());
        actual.setDistrito(agricultor.getDistrito());
        actual.setActivo(agricultor.getActivo());

        return agricultorRepository.save(actual);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Agricultor agricultor = obtenerPorId(id);
        agricultorRepository.delete(agricultor);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Agricultor> buscarPorDni(String dni) {
        return agricultorRepository.findByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Agricultor> buscarPorEmail(String email) {
        return agricultorRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agricultor> listarActivos() {
        return agricultorRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorDni(String dni) {
        return agricultorRepository.existsByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorEmail(String email) {
        return agricultorRepository.existsByEmail(email);
    }

    private void validarDniYEmailUnicos(Agricultor agricultor) {
        if (agricultorRepository.existsByDni(agricultor.getDni())) {
            throw new RuntimeException("Ya existe un agricultor con el DNI: " + agricultor.getDni());
        }
        if (agricultor.getEmail() != null && agricultorRepository.existsByEmail(agricultor.getEmail())) {
            throw new RuntimeException("Ya existe un agricultor con el email: " + agricultor.getEmail());
        }
    }
}
