package com.upc.smartharvest.repository;

import com.upc.smartharvest.entities.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgricultorRepository extends JpaRepository<Agricultor, Long> {

    Optional<Agricultor> findByDni(String dni);

    Optional<Agricultor> findByEmail(String email);

    List<Agricultor> findByActivoTrue();

    boolean existsByDni(String dni);

    boolean existsByEmail(String email);
}
