package com.upc.smartharvest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap; // Salga en el mismo orden en que se agrega los datos.
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/landing")
public class LandingController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerLanding() {

        Map<String, Object> landing = new LinkedHashMap<>();

        landing.put("titulo", "SmartHarvest");
        landing.put("descripcion", "SmartHarvest es una plataforma agrícola inteligente que permite monitorear cultivos, gestionar parcelas, visualizar alertas y tomar mejores decisiones para mejorar la productividad agrícola.");
        landing.put("beneficios", List.of(
                "Monitoreo de parcelas y cultivos",
                "Visualización de alertas importantes",
                "Gestión de tareas agrícolas",
                "Consulta de datos climáticos",
                "Seguimiento de sensores en tiempo real"
        ));
        landing.put("botonLogin", "Iniciar sesión");
        landing.put("rutaLogin", "/login");

        return new ResponseEntity<>(landing, HttpStatus.OK);
    }
}