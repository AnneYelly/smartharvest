package com.upc.smartharvest.repository;

import com.upc.smartharvest.entities.SensorLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorLotRepository extends JpaRepository<SensorLot, Long> {

    List<SensorLot> findByParcelaId(Long parcelaId);

    Optional<SensorLot> findByCodigo(String codigo);

    List<SensorLot> findByParcelaIdAndEstado(Long parcelaId, String estado);

    boolean existsByCodigo(String codigo);
}
