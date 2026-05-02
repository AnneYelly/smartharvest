package com.upc.smartharvest.DTOS;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DatoClimaticoDTO {
    private Long id;
    private Long parcelaId;
    private LocalDate fecha;
    private String fuente;
    private BigDecimal temperaturaMin;
    private BigDecimal temperaturaMax;
    private BigDecimal humedadPorcentaje;
    private BigDecimal precipitacionMm;
    private BigDecimal velocidadVientoKmh;
    private BigDecimal indiceUv;
    private String condicion;
    private LocalDateTime createdAt;
}
