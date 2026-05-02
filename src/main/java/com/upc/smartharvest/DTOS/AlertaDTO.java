package com.upc.smartharvest.DTOS;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AlertaDTO {
    private Long id;
    private Long parcelaId;
    private Long lecturaId;
    private String tipoAlerta;
    private String nivel;
    private String mensaje;
    private String estado;
    private Long usuarioResolverId;
    private LocalDateTime fechaResolucion;
    private LocalDateTime fechaHora;
    private LocalDateTime createdAt;
}
