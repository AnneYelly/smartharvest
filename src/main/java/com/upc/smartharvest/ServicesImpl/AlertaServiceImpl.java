package com.upc.smartharvest.ServicesImpl;

import com.upc.smartharvest.entities.Alerta;
import com.upc.smartharvest.repository.AlertaRepository;
import com.upc.smartharvest.services.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaServiceImpl implements AlertaService {

    private final AlertaRepository alertaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Alerta> listar() {
        return alertaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Alerta obtenerPorId(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public Alerta registrar(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    @Override
    @Transactional
    public Alerta actualizar(Long id, Alerta alerta) {
        Alerta actual = obtenerPorId(id);

        actual.setParcela(alerta.getParcela());
        actual.setLectura(alerta.getLectura());
        actual.setTipoAlerta(alerta.getTipoAlerta());
        actual.setNivel(alerta.getNivel());
        actual.setMensaje(alerta.getMensaje());
        actual.setEstado(alerta.getEstado());
        actual.setUsuarioResolver(alerta.getUsuarioResolver());
        actual.setFechaResolucion(alerta.getFechaResolucion());
        actual.setFechaHora(alerta.getFechaHora());

        return alertaRepository.save(actual);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Alerta alerta = obtenerPorId(id);
        alertaRepository.delete(alerta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alerta> listarPorParcela(Long parcelaId) {
        return alertaRepository.findByParcelaId(parcelaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alerta> listarPorEstado(String estado) {
        return alertaRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alerta> listarPorParcelaYEstado(Long parcelaId, String estado) {
        return alertaRepository.findByParcelaIdAndEstado(parcelaId, estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alerta> listarPorNivel(String nivel) {
        return alertaRepository.findByNivel(nivel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alerta> listarPorParcelaOrdenFechaDesc(Long parcelaId) {
        return alertaRepository.findByParcelaIdOrderByFechaHoraDesc(parcelaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alerta> listarPorNivelYEstado(String nivel, String estado) {
        return alertaRepository.findByNivelAndEstado(nivel, estado);
    }
}
