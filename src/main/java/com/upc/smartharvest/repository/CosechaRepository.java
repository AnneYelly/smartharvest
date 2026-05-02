package com.upc.smartharvest.repository;

import com.upc.smartharvest.entities.Cosecha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CosechaRepository extends JpaRepository<Cosecha, Long> {

    List<Cosecha> findByCultivoId(Long cultivoId);

    List<Cosecha> findByCultivoIdOrderByFechaCosechaDesc(Long cultivoId);

    List<Cosecha> findByFechaCosechaBetween(LocalDate inicio, LocalDate fin);

    List<Cosecha> findByCultivoParcelaId(Long parcelaId);
}
