package com.upc.smartharvest.ServicesImpl;

import com.upc.smartharvest.entities.Parcela;
import com.upc.smartharvest.repository.ParcelaRepository;
import com.upc.smartharvest.services.ParcelaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.upc.smartharvest.Exception.RecursoNoEncontradoException;
import com.upc.smartharvest.Exception.ValidacionException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelaServiceImpl implements ParcelaService {

    private final ParcelaRepository parcelaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Parcela> listar() {
        return parcelaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Parcela obtenerPorId(Long id) {
        return parcelaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Parcela no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public Parcela registrar(Parcela parcela) {

        if (parcela.getAgricultor() == null || parcela.getAgricultor().getId() == null) {
            throw new ValidacionException("Debe seleccionar un agricultor para registrar la parcela");
        }

        if (parcela.getNombre() == null || parcela.getNombre().trim().isEmpty()) {
            throw new ValidacionException("El nombre de la parcela es obligatorio");
        }

        if (parcela.getLatitud() == null || parcela.getLongitud() == null) {
            throw new ValidacionException("La latitud y longitud son obligatorias");
        }

        return parcelaRepository.save(parcela);
    }

    @Override
    @Transactional
    public Parcela actualizar(Long id, Parcela parcela) {
        Parcela actual = obtenerPorId(id);

        if (parcela.getAgricultor() == null || parcela.getAgricultor().getId() == null) {
            throw new ValidacionException("Debe seleccionar un agricultor para actualizar la parcela");
        }

        if (parcela.getNombre() == null || parcela.getNombre().trim().isEmpty()) {
            throw new ValidacionException("El nombre de la parcela es obligatorio");
        }

        if (parcela.getLatitud() == null || parcela.getLongitud() == null) {
            throw new ValidacionException("La latitud y longitud son obligatorias");
        }

        actual.setAgricultor(parcela.getAgricultor());
        actual.setNombre(parcela.getNombre());
        actual.setUbicacion(parcela.getUbicacion());
        actual.setLatitud(parcela.getLatitud());
        actual.setLongitud(parcela.getLongitud());
        actual.setAltitudMsnm(parcela.getAltitudMsnm());
        actual.setTamanoHectareas(parcela.getTamanoHectareas());
        actual.setTipoSuelo(parcela.getTipoSuelo());
        actual.setFuenteAgua(parcela.getFuenteAgua());
        actual.setActivo(parcela.getActivo());

        return parcelaRepository.save(actual);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Parcela parcela = obtenerPorId(id);
        parcelaRepository.delete(parcela);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parcela> listarPorAgricultor(Long agricultorId) {
        return parcelaRepository.findByAgricultorId(agricultorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parcela> listarActivasPorAgricultor(Long agricultorId) {
        return parcelaRepository.findByAgricultorIdAndActivoTrue(agricultorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Parcela> listarActivas() {
        return parcelaRepository.findByActivoTrue();
    }
}
