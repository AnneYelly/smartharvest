package com.upc.smartharvest.controllers;

import com.upc.smartharvest.DTOS.DashboardDTO;
import com.upc.smartharvest.repository.AlertaRepository;
import com.upc.smartharvest.repository.CosechaRepository;
import com.upc.smartharvest.repository.CultivoRepository;
import com.upc.smartharvest.repository.ParcelaRepository;
import com.upc.smartharvest.repository.SensorLotRepository;
import com.upc.smartharvest.repository.TareaAgricolaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final ParcelaRepository parcelaRepository;
    private final CultivoRepository cultivoRepository;
    private final SensorLotRepository sensorLotRepository;
    private final AlertaRepository alertaRepository;
    private final TareaAgricolaRepository tareaAgricolaRepository;
    private final CosechaRepository cosechaRepository;

    public DashboardController(
            ParcelaRepository parcelaRepository,
            CultivoRepository cultivoRepository,
            SensorLotRepository sensorLotRepository,
            AlertaRepository alertaRepository,
            TareaAgricolaRepository tareaAgricolaRepository,
            CosechaRepository cosechaRepository
    ) {
        this.parcelaRepository = parcelaRepository;
        this.cultivoRepository = cultivoRepository;
        this.sensorLotRepository = sensorLotRepository;
        this.alertaRepository = alertaRepository;
        this.tareaAgricolaRepository = tareaAgricolaRepository;
        this.cosechaRepository = cosechaRepository;
    }

    @GetMapping("/resumen")
    public ResponseEntity<DashboardDTO> obtenerResumenDashboard() {

        DashboardDTO dashboardDTO = new DashboardDTO();

        dashboardDTO.setTotalParcelasActivas(
                (long) parcelaRepository.findByActivoTrue().size()
        );

        dashboardDTO.setTotalCultivosActivos(
                (long) cultivoRepository.findByEstado("ACTIVO").size()
        );

        dashboardDTO.setTotalSensoresActivos(
                sensorLotRepository.findAll()
                        .stream()
                        .filter(sensor -> "ACTIVO".equalsIgnoreCase(sensor.getEstado()))
                        .count()
        );

        dashboardDTO.setTotalAlertasPendientes(
                (long) alertaRepository.findByEstado("PENDIENTE").size()
        );

        dashboardDTO.setTotalTareasPendientes(
                tareaAgricolaRepository.findAll()
                        .stream()
                        .filter(tarea -> "PENDIENTE".equalsIgnoreCase(tarea.getEstado()))
                        .count()
        );

        dashboardDTO.setTotalCosechas(
                cosechaRepository.count()
        );

        return new ResponseEntity<>(dashboardDTO, HttpStatus.OK);
    }
}