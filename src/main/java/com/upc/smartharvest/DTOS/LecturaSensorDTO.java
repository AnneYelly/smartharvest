package com.upc.smartharvest.DTOS;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LecturaSensorDTO {
    private Long id;
    private Long sensorId;
    private BigDecimal valor;
    private String unidadMedida;
    private Boolean esAnomalia;
    private LocalDateTime fechaHora;
}
