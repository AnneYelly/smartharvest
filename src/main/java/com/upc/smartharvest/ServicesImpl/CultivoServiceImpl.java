package com.upc.smartharvest.ServicesImpl;

import com.upc.smartharvest.entities.Cultivo;
import com.upc.smartharvest.repository.CultivoRepository;
import com.upc.smartharvest.services.CultivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CultivoServiceImpl implements CultivoService {

    private final CultivoRepository cultivoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> listar() {
        return cultivoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cultivo obtenerPorId(Long id) {
        return cultivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cultivo no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Cultivo registrar(Cultivo cultivo) {
        return cultivoRepository.save(cultivo);
    }

    @Override
    @Transactional
    public Cultivo actualizar(Long id, Cultivo cultivo) {
        Cultivo actual = obtenerPorId(id);

        actual.setParcela(cultivo.getParcela());
        actual.setNombre(cultivo.getNombre());
        actual.setVariedad(cultivo.getVariedad());
        actual.setFechaSiembra(cultivo.getFechaSiembra());
        actual.setFechaCosechaEstimada(cultivo.getFechaCosechaEstimada());
        actual.setFechaCosechaReal(cultivo.getFechaCosechaReal());
        actual.setEstado(cultivo.getEstado());
        actual.setSuperficieHectareas(cultivo.getSuperficieHectareas());
        actual.setRendimientoEsperadoKg(cultivo.getRendimientoEsperadoKg());

        return cultivoRepository.save(actual);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Cultivo cultivo = obtenerPorId(id);

        if (cultivo == null) {
            throw new RuntimeException("Cultivo no encontrado");
        }

        cultivo.setEstado("INACTIVO");
        cultivoRepository.save(cultivo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> listarPorParcela(Long parcelaId) {
        return cultivoRepository.findByParcelaId(parcelaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> listarPorParcelaYEstado(Long parcelaId, String estado) {
        return cultivoRepository.findByParcelaIdAndEstado(parcelaId, estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> listarPorEstado(String estado) {
        return cultivoRepository.findByEstado(estado);
    }
}
