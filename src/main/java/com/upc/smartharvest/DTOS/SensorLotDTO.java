package com.upc.smartharvest.DTOS;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SensorLotDTO {
    private Long id;
    private Long parcelaId;
    private String codigo;
    private String tipoSensor;
    private String unidadMedida;
    private String estado;
    private BigDecimal umbralMin;
    private BigDecimal umbralMax;
    private BigDecimal bateriaPorcentaje;
    private LocalDateTime ultimaLecturaAt;
    private LocalDate fechaInstalacion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
