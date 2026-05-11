package com.upc.smartharvest.ServicesImpl;

import com.upc.smartharvest.entities.TareaAgricola;
import com.upc.smartharvest.repository.TareaAgricolaRepository;
import com.upc.smartharvest.services.TareaAgricolaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TareaAgricolaServiceImpl implements TareaAgricolaService {

    private final TareaAgricolaRepository tareaAgricolaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TareaAgricola> listar() {
        return tareaAgricolaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TareaAgricola obtenerPorId(Long id) {
        return tareaAgricolaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea agrícola no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public TareaAgricola registrar(TareaAgricola tareaAgricola) {
        return tareaAgricolaRepository.save(tareaAgricola);
    }

    @Override
    @Transactional
    public TareaAgricola actualizar(Long id, TareaAgricola tareaAgricola) {
        TareaAgricola actual = obtenerPorId(id);

        actual.setParcela(tareaAgricola.getParcela());
        actual.setCultivo(tareaAgricola.getCultivo());
        actual.setUsuario(tareaAgricola.getUsuario());
        actual.setTipoTarea(tareaAgricola.getTipoTarea());
        actual.setDescripcion(tareaAgricola.getDescripcion());
        actual.setFechaProgramada(tareaAgricola.getFechaProgramada());
        actual.setFechaEjecucion(tareaAgricola.getFechaEjecucion());
        actual.setEstado(tareaAgricola.getEstado());
        actual.setPrioridad(tareaAgricola.getPrioridad());
        actual.setCostoEstimado(tareaAgricola.getCostoEstimado());
        actual.setCostoReal(tareaAgricola.getCostoReal());

        return tareaAgricolaRepository.save(actual);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        TareaAgricola tareaAgricola = obtenerPorId(id);
        tareaAgricolaRepository.delete(tareaAgricola);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaAgricola> listarPorParcela(Long parcelaId) {
        return tareaAgricolaRepository.findByParcelaId(parcelaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaAgricola> listarPorCultivo(Long cultivoId) {
        return tareaAgricolaRepository.findByCultivoId(cultivoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaAgricola> listarPorUsuario(Long usuarioId) {
        return tareaAgricolaRepository.findByUsuarioId(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaAgricola> listarPorParcelaYEstado(Long parcelaId, String estado) {
        return tareaAgricolaRepository.findByParcelaIdAndEstado(parcelaId, estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaAgricola> listarPorUsuarioYEstado(Long usuarioId, String estado) {
        return tareaAgricolaRepository.findByUsuarioIdAndEstado(usuarioId, estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaAgricola> listarPorFechaProgramadaEntre(LocalDate inicio, LocalDate fin) {
        return tareaAgricolaRepository.findByFechaProgramadaBetween(inicio, fin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaAgricola> filtrarTareas(String busqueda, String estado, LocalDate fechaInicio, LocalDate fechaFin) {
        return tareaAgricolaRepository.findAll()
                .stream()
                .filter(tarea -> {
                    if (busqueda == null || busqueda.trim().isEmpty()) {
                        return true;
                    }

                    String texto = busqueda.trim().toLowerCase();

                    return (tarea.getTipoTarea() != null && tarea.getTipoTarea().toLowerCase().contains(texto))
                            || (tarea.getDescripcion() != null && tarea.getDescripcion().toLowerCase().contains(texto))
                            || (tarea.getPrioridad() != null && tarea.getPrioridad().toLowerCase().contains(texto))
                            || (tarea.getEstado() != null && tarea.getEstado().toLowerCase().contains(texto));
                })
                .filter(tarea -> {
                    if (estado == null || estado.trim().isEmpty()) {
                        return true;
                    }

                    return tarea.getEstado() != null
                            && tarea.getEstado().equalsIgnoreCase(estado.trim());
                })
                .filter(tarea -> {
                    if (fechaInicio == null) {
                        return true;
                    }

                    return tarea.getFechaProgramada() != null
                            && !tarea.getFechaProgramada().isBefore(fechaInicio);
                })
                .filter(tarea -> {
                    if (fechaFin == null) {
                        return true;
                    }

                    return tarea.getFechaProgramada() != null
                            && !tarea.getFechaProgramada().isAfter(fechaFin);
                })
                .toList();
    }
}
