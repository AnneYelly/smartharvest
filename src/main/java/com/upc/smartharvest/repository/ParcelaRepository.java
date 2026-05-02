package com.upc.smartharvest.repository;

import com.upc.smartharvest.entities.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelaRepository extends JpaRepository<Parcela, Long> {

    List<Parcela> findByAgricultorId(Long agricultorId);

    List<Parcela> findByAgricultorIdAndActivoTrue(Long agricultorId);

    List<Parcela> findByActivoTrue();
}
