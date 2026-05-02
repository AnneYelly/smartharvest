package com.upc.smartharvest.DTOS;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AgricultorDTO {
    private Long id;
    private String dni;
    private String ruc;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String departamento;
    private String provincia;
    private String distrito;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
