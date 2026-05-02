package com.upc.smartharvest.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "parcela")
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agricultor_id", nullable = false)
    private Agricultor agricultor;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 150)
    private String ubicacion;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitud;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitud;

    @Column(name = "altitud_msnm", precision = 7, scale = 1)
    private BigDecimal altitudMsnm;

    @Column(name = "tamano_hectareas", precision = 10, scale = 2)
    private BigDecimal tamanoHectareas;

    @Column(name = "tipo_suelo", length = 50)
    private String tipoSuelo;

    @Column(name = "fuente_agua", length = 80)
    private String fuenteAgua;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
