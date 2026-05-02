package com.upc.smartharvest.ServicesImpl;

import com.upc.smartharvest.entities.DatoClimatico;
import com.upc.smartharvest.repository.DatoClimaticoRepository;
import com.upc.smartharvest.services.DatoClimaticoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatoClimaticoServiceImpl implements DatoClimaticoService {

    private final DatoClimaticoRepository datoClimaticoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DatoClimatico> listar() {
        return datoClimaticoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public DatoClimatico obtenerPorId(Long id) {
        return datoClimaticoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dato climático no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public DatoClimatico registrar(DatoClimatico datoClimatico) {
        return datoClimaticoRepository.save(datoClimatico);
    }

    @Override
    @Transactional
    public DatoClimatico actualizar(Long id, DatoClimatico datoClimatico) {
        DatoClimatico actual = obtenerPorId(id);

        actual.setParcela(datoClimatico.getParcela());
        actual.setFecha(datoClimatico.getFecha());
        actual.setFuente(datoClimatico.getFuente());
        actual.setTemperaturaMin(datoClimatico.getTemperaturaMin());
        actual.setTemperaturaMax(datoClimatico.getTemperaturaMax());
        actual.setHumedadPorcentaje(datoClimatico.getHumedadPorcentaje());
        actual.setPrecipitacionMm(datoClimatico.getPrecipitacionMm());
        actual.setVelocidadVientoKmh(datoClimatico.getVelocidadVientoKmh());
        actual.setIndiceUv(datoClimatico.getIndiceUv());
        actual.setCondicion(datoClimatico.getCondicion());

        return datoClimaticoRepository.save(actual);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        DatoClimatico datoClimatico = obtenerPorId(id);
        datoClimaticoRepository.delete(datoClimatico);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DatoClimatico> listarPorParcela(Long parcelaId) {
        return datoClimaticoRepository.findByParcelaId(parcelaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DatoClimatico> listarPorParcelaYFechaEntre(Long parcelaId, LocalDate inicio, LocalDate fin) {
        return datoClimaticoRepository.findByParcelaIdAndFechaBetween(parcelaId, inicio, fin);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DatoClimatico> buscarPorParcelaYFecha(Long parcelaId, LocalDate fecha) {
        return datoClimaticoRepository.findByParcelaIdAndFecha(parcelaId, fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DatoClimatico> listarPorParcelaOrdenFechaDesc(Long parcelaId) {
        return datoClimaticoRepository.findByParcelaIdOrderByFechaDesc(parcelaId);
    }
}
