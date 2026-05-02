package com.upc.smartharvest.repository;

import com.upc.smartharvest.entities.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByParcelaId(Long parcelaId);

    List<Alerta> findByEstado(String estado);

    List<Alerta> findByParcelaIdAndEstado(Long parcelaId, String estado);

    List<Alerta> findByNivel(String nivel);

    List<Alerta> findByParcelaIdOrderByFechaHoraDesc(Long parcelaId);

    List<Alerta> findByNivelAndEstado(String nivel, String estado);
}
