package com.upc.smartharvest.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cosecha")
public class Cosecha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cultivo_id", nullable = false)
    private Cultivo cultivo;

    @Column(name = "fecha_cosecha", nullable = false)
    private LocalDate fechaCosecha;

    @Column(name = "cantidad_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadKg;

    @Column(name = "cantidad_descarte_kg", precision = 10, scale = 2)
    private BigDecimal cantidadDescarteKg;

    @Column(length = 30)
    private String calidad;

    @Column(name = "metodo_cosecha", length = 50)
    private String metodoCosecha;

    @Column(name = "precio_venta_kg", precision = 10, scale = 2)
    private BigDecimal precioVentaKg;

    @Column(length = 150)
    private String destino;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

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
