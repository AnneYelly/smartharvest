package com.upc.smartharvest.DTOS;

import lombok.Data;

@Data
public class DashboardDTO {

    private Long totalParcelasActivas;
    private Long totalCultivosActivos;
    private Long totalSensoresActivos;
    private Long totalAlertasPendientes;
    private Long totalTareasPendientes;
    private Long totalCosechas;
}