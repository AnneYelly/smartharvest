package com.upc.smartharvest.DTOS;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CosechaDTO {
    private Long id;
    private Long cultivoId;
    private LocalDate fechaCosecha;
    private BigDecimal cantidadKg;
    private BigDecimal cantidadDescarteKg;
    private String calidad;
    private String metodoCosecha;
    private BigDecimal precioVentaKg;
    private String destino;
    private String observaciones;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
