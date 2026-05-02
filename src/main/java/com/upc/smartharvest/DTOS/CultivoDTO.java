package com.upc.smartharvest.DTOS;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CultivoDTO {
    private Long id;
    private Long parcelaId;
    private String nombre;
    private String variedad;
    private LocalDate fechaSiembra;
    private LocalDate fechaCosechaEstimada;
    private LocalDate fechaCosechaReal;
    private String estado;
    private BigDecimal superficieHectareas;
    private BigDecimal rendimientoEsperadoKg;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
