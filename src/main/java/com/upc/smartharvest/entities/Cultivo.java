package com.upc.smartharvest.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cultivo")
public class Cultivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcela_id", nullable = false)
    private Parcela parcela;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(length = 80)
    private String variedad;

    @Column(name = "fecha_siembra")
    private LocalDate fechaSiembra;

    @Column(name = "fecha_cosecha_estimada")
    private LocalDate fechaCosechaEstimada;

    @Column(name = "fecha_cosecha_real")
    private LocalDate fechaCosechaReal;

    @Column(nullable = false, length = 30)
    private String estado;

    @Column(name = "superficie_hectareas", precision = 10, scale = 2)
    private BigDecimal superficieHectareas;

    @Column(name = "rendimiento_esperado_kg", precision = 10, scale = 2)
    private BigDecimal rendimientoEsperadoKg;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (estado == null) estado = "ACTIVO";
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
