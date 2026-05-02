package com.upc.smartharvest.DTOS;

import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class UsuarioDTO {
    private Long id;
    private Long agricultorId;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;
    private String rol;
    private String estado;
    private LocalDateTime ultimaAcceso;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
