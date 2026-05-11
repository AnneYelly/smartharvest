package com.upc.smartharvest.ServicesImpl;

import com.upc.smartharvest.DTOS.ReporteCosechaDTO;
import com.upc.smartharvest.entities.Cosecha;
import com.upc.smartharvest.repository.CosechaRepository;
import com.upc.smartharvest.services.CosechaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CosechaServiceImpl implements CosechaService {

    private final CosechaRepository cosechaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cosecha> listar() {
        return cosechaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cosecha obtenerPorId(Long id) {
        return cosechaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cosecha no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public Cosecha registrar(Cosecha cosecha) {
        return cosechaRepository.save(cosecha);
    }

    @Override
    @Transactional
    public Cosecha actualizar(Long id, Cosecha cosecha) {
        Cosecha actual = obtenerPorId(id);

        actual.setCultivo(cosecha.getCultivo());
        actual.setFechaCosecha(cosecha.getFechaCosecha());
        actual.setCantidadKg(cosecha.getCantidadKg());
        actual.setCantidadDescarteKg(cosecha.getCantidadDescarteKg());
        actual.setCalidad(cosecha.getCalidad());
        actual.setMetodoCosecha(cosecha.getMetodoCosecha());
        actual.setPrecioVentaKg(cosecha.getPrecioVentaKg());
        actual.setDestino(cosecha.getDestino());
        actual.setObservaciones(cosecha.getObservaciones());

        return cosechaRepository.save(actual);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Cosecha cosecha = obtenerPorId(id);
        cosechaRepository.delete(cosecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cosecha> listarPorCultivo(Long cultivoId) {
        return cosechaRepository.findByCultivoId(cultivoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cosecha> listarPorCultivoOrdenFechaDesc(Long cultivoId) {
        return cosechaRepository.findByCultivoIdOrderByFechaCosechaDesc(cultivoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cosecha> listarPorFechaCosechaEntre(LocalDate inicio, LocalDate fin) {
        return cosechaRepository.findByFechaCosechaBetween(inicio, fin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cosecha> listarPorParcela(Long parcelaId) {
        return cosechaRepository.findByCultivoParcelaId(parcelaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteCosechaDTO> generarReporteCosecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return cosechaRepository.obtenerReporteCosecha(fechaInicio, fechaFin);
    }

}
