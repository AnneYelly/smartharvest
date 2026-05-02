package com.upc.smartharvest.repository;

import com.upc.smartharvest.entities.DatoClimatico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DatoClimaticoRepository extends JpaRepository<DatoClimatico, Long> {

    List<DatoClimatico> findByParcelaId(Long parcelaId);

    List<DatoClimatico> findByParcelaIdAndFechaBetween(Long parcelaId, LocalDate inicio, LocalDate fin);

    Optional<DatoClimatico> findByParcelaIdAndFecha(Long parcelaId, LocalDate fecha);

    List<DatoClimatico> findByParcelaIdOrderByFechaDesc(Long parcelaId);
}
