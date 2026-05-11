package com.upc.smartharvest.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteCosechaDTO {

    private String nombreCultivo;
    private BigDecimal totalKg;
    private Long cantidadRegistros;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
    }

}
