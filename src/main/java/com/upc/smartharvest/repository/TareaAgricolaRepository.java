package com.upc.smartharvest.repository;

import com.upc.smartharvest.entities.TareaAgricola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TareaAgricolaRepository extends JpaRepository<TareaAgricola, Long> {

    List<TareaAgricola> findByParcelaId(Long parcelaId);

    List<TareaAgricola> findByCultivoId(Long cultivoId);

    List<TareaAgricola> findByUsuarioId(Long usuarioId);

    List<TareaAgricola> findByParcelaIdAndEstado(Long parcelaId, String estado);

    List<TareaAgricola> findByUsuarioIdAndEstado(Long usuarioId, String estado);

    List<TareaAgricola> findByFechaProgramadaBetween(LocalDate inicio, LocalDate fin);
}
