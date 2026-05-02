package com.upc.smartharvest.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sensor_lot")
public class SensorLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parcela_id", nullable = false)
    private Parcela parcela;

    @Column(nullable = false, length = 50, unique = true)
    private String codigo;

    @Column(name = "tipo_sensor", length = 50)
    private String tipoSensor;

    @Column(name = "unidad_medida", length = 20)
    private String unidadMedida;

    @Column(nullable = false, length = 30)
    private String estado;

    @Column(name = "umbral_min", precision = 10, scale = 2)
    private BigDecimal umbralMin;

    @Column(name = "umbral_max", precision = 10, scale = 2)
    private BigDecimal umbralMax;

    @Column(name = "bateria_porcentaje", precision = 5, scale = 2)
    private BigDecimal bateriaPorcentaje;

    @Column(name = "ultima_lectura_at")
    private LocalDateTime ultimaLecturaAt;

    @Column(name = "fecha_instalacion")
    private LocalDate fechaInstalacion;

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
