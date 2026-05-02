package com.upc.smartharvest.DTOS;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TareaAgricolaDTO {
    private Long id;
    private Long parcelaId;
    private Long cultivoId;
    private Long usuarioId;
    private String tipoTarea;
    private String descripcion;
    private LocalDate fechaProgramada;
    private LocalDate fechaEjecucion;
    private String estado;
    private String prioridad;
    private BigDecimal costoEstimado;
    private BigDecimal costoReal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
