package com.upc.smartharvest.DTOS;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private Long id;
    private String username;
    private String rol;
    private String estado;
    private Long agricultorId;
    private String mensaje;
}