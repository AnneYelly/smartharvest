package com.upc.smartharvest.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "dato_climatico")
public class DatoClimatico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcela_id", nullable = false)
    private Parcela parcela;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, length = 50)
    private String fuente;

    @Column(name = "temperatura_min", precision = 5, scale = 2)
    private BigDecimal temperaturaMin;

    @Column(name = "temperatura_max", precision = 5, scale = 2)
    private BigDecimal temperaturaMax;

    @Column(name = "humedad_porcentaje", precision = 5, scale = 2)
    private BigDecimal humedadPorcentaje;

    @Column(name = "precipitacion_mm", precision = 7, scale = 2)
    private BigDecimal precipitacionMm;

    @Column(name = "velocidad_viento_kmh", precision = 6, scale = 2)
    private BigDecimal velocidadVientoKmh;

    @Column(name = "indice_uv", precision = 4, scale = 1)
    private BigDecimal indiceUv;

    @Column(length = 50)
    private String condicion;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
