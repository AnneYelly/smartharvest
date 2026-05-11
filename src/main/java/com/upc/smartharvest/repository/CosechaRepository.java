package com.upc.smartharvest.repository;

import com.upc.smartharvest.DTOS.ReporteCosechaDTO;
import com.upc.smartharvest.entities.Cosecha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CosechaRepository extends JpaRepository<Cosecha, Long> {

    List<Cosecha> findByCultivoId(Long cultivoId);

    List<Cosecha> findByCultivoIdOrderByFechaCosechaDesc(Long cultivoId);

    List<Cosecha> findByFechaCosechaBetween(LocalDate inicio, LocalDate fin);

    List<Cosecha> findByCultivoParcelaId(Long parcelaId);

    @Query(value = "SELECT c.nombre AS nombreCultivo, " +
            "SUM(co.cantidad_kg) AS totalKG, " +
            "COUNT(co.id) AS cantidadRegistros " +
            "FROM cosecha co " +
            "INNER JOIN cultivo c ON co.cultivo_id = c.id " +
            "WHERE co.fecha_cosecha BETWEEN ?1 AND ?2 " +
            "GROUP BY c.nombre " +
            "ORDER BY totalKg DESC", nativeQuery = true)
    List<ReporteCosechaDTO> obtenerReporteCosecha(LocalDate fechaInicio, LocalDate fechaFin);

}
