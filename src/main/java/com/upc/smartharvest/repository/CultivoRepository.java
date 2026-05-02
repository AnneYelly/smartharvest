package com.upc.smartharvest.repository;

import com.upc.smartharvest.entities.Cultivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Long> {

    List<Cultivo> findByParcelaId(Long parcelaId);

    List<Cultivo> findByParcelaIdAndEstado(Long parcelaId, String estado);

    List<Cultivo> findByEstado(String estado);
}
