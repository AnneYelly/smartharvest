package com.upc.smartharvest.repository;

import com.upc.smartharvest.entities.LecturaSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LecturaSensorRepository extends JpaRepository<LecturaSensor, Long> {

    List<LecturaSensor> findBySensorId(Long sensorId);

    List<LecturaSensor> findBySensorIdOrderByFechaHoraDesc(Long sensorId);

    List<LecturaSensor> findBySensorIdAndFechaHoraBetween(Long sensorId, LocalDateTime inicio, LocalDateTime fin);

    List<LecturaSensor> findByEsAnomaliaTrue();

    List<LecturaSensor> findBySensorIdAndEsAnomaliaTrue(Long sensorId);
}
