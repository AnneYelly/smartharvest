package com.upc.smartharvest.DTOS;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ParcelaDTO {
    private Long id;
    private Long agricultorId;
    private String nombre;
    private String ubicacion;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private BigDecimal altitudMsnm;
    private BigDecimal tamanoHectareas;
    private String tipoSuelo;
    private String fuenteAgua;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
