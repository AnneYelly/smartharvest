package com.upc.smartharvest.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "lectura_sensor")
public class LecturaSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private SensorLot sensor;

    @Column(nullable = false, precision = 14, scale = 3)
    private BigDecimal valor;

    @Column(name = "unidad_medida", length = 20)
    private String unidadMedida;

    @Column(name = "es_anomalia", nullable = false)
    private Boolean esAnomalia = false;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @PrePersist
    protected void onCreate() {
        if (fechaHora == null) fechaHora = LocalDateTime.now();
        if (esAnomalia == null) esAnomalia = false;
    }
}
