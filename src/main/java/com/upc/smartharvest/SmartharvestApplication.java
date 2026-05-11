package com.upc.smartharvest;

import com.upc.smartharvest.entities.*;
import com.upc.smartharvest.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class SmartharvestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartharvestApplication.class, args);
    }
    @Bean
    CommandLineRunner initData(
            AgricultorRepository agricultorRepository,
            ParcelaRepository parcelaRepository,
            UsuarioRepository usuarioRepository,
            CultivoRepository cultivoRepository,
            SensorLotRepository sensorLotRepository,
            LecturaSensorRepository lecturaSensorRepository,
            AlertaRepository alertaRepository,
            DatoClimaticoRepository datoClimaticoRepository,
            TareaAgricolaRepository tareaAgricolaRepository,
            CosechaRepository cosechaRepository
    ) {
        return args -> {

            // Evita duplicar datos cada vez que levantas el proyecto
            if (agricultorRepository.existsByDni("12345678")) {
                System.out.println("Datos de prueba ya existen. No se insertó nuevamente.");
                return;
            }

            // AGRICULTOR
            Agricultor agricultor = new Agricultor();
            agricultor.setDni("12345678");
            agricultor.setRuc("20123456789");
            agricultor.setNombres("Juan");
            agricultor.setApellidos("Pérez Ramos");
            agricultor.setEmail("juan.perez@smartharvest.com");
            agricultor.setTelefono("987654321");
            agricultor.setDepartamento("Lima");
            agricultor.setProvincia("Lima");
            agricultor.setDistrito("Ate");
            agricultor.setActivo(true);
            agricultor = agricultorRepository.save(agricultor);

            // PARCELA
            Parcela parcela = new Parcela();
            parcela.setAgricultor(agricultor);
            parcela.setNombre("Parcela Los Olivos");
            parcela.setUbicacion("Zona agrícola de Ate");
            parcela.setLatitud(new BigDecimal("-12.046374"));
            parcela.setLongitud(new BigDecimal("-76.920000"));
            parcela.setAltitudMsnm(new BigDecimal("355.5"));
            parcela.setTamanoHectareas(new BigDecimal("2.50"));
            parcela.setTipoSuelo("Franco arenoso");
            parcela.setFuenteAgua("Pozo");
            parcela.setActivo(true);
            parcela = parcelaRepository.save(parcela);

            // USUARIO
            Usuario usuario = new Usuario();
            usuario.setAgricultor(agricultor);
            usuario.setUsername("jperez");
            usuario.setPasswordHash("123456"); // Solo prueba. En producción debe ir encriptado.
            usuario.setRol("AGRICULTOR");
            usuario.setEstado("ACTIVO");
            usuario.setUltimaAcceso(LocalDateTime.now());
            usuario = usuarioRepository.save(usuario);

            // CULTIVO
            Cultivo cultivo = new Cultivo();
            cultivo.setParcela(parcela);
            cultivo.setNombre("Papa");
            cultivo.setVariedad("Yungay");
            cultivo.setFechaSiembra(LocalDate.now().minusMonths(2));
            cultivo.setFechaCosechaEstimada(LocalDate.now().plusMonths(3));
            cultivo.setEstado("ACTIVO");
            cultivo.setSuperficieHectareas(new BigDecimal("1.20"));
            cultivo.setRendimientoEsperadoKg(new BigDecimal("3500.00"));
            cultivo = cultivoRepository.save(cultivo);

            // SENSOR
            SensorLot sensor = new SensorLot();
            sensor.setParcela(parcela);
            sensor.setCodigo("SENSOR-001");
            sensor.setTipoSensor("HUMEDAD");
            sensor.setUnidadMedida("%");
            sensor.setEstado("ACTIVO");
            sensor.setUmbralMin(new BigDecimal("30.00"));
            sensor.setUmbralMax(new BigDecimal("80.00"));
            sensor.setBateriaPorcentaje(new BigDecimal("95.50"));
            sensor.setUltimaLecturaAt(LocalDateTime.now());
            sensor.setFechaInstalacion(LocalDate.now().minusDays(10));
            sensor = sensorLotRepository.save(sensor);

            // LECTURA SENSOR
            LecturaSensor lectura = new LecturaSensor();
            lectura.setSensor(sensor);
            lectura.setValor(new BigDecimal("25.500"));
            lectura.setUnidadMedida("%");
            lectura.setEsAnomalia(true);
            lectura.setFechaHora(LocalDateTime.now());
            lectura = lecturaSensorRepository.save(lectura);

            // ALERTA
            Alerta alerta = new Alerta();
            alerta.setParcela(parcela);
            alerta.setLectura(lectura);
            alerta.setTipoAlerta("HUMEDAD_BAJA");
            alerta.setNivel("ALTO");
            alerta.setMensaje("La humedad del suelo está por debajo del umbral recomendado.");
            alerta.setEstado("PENDIENTE");
            alerta.setFechaHora(LocalDateTime.now());
            alertaRepository.save(alerta);

            // DATO CLIMÁTICO
            DatoClimatico datoClimatico = new DatoClimatico();
            datoClimatico.setParcela(parcela);
            datoClimatico.setFecha(LocalDate.now());
            datoClimatico.setFuente("SENAMHI");
            datoClimatico.setTemperaturaMin(new BigDecimal("16.50"));
            datoClimatico.setTemperaturaMax(new BigDecimal("27.80"));
            datoClimatico.setHumedadPorcentaje(new BigDecimal("65.00"));
            datoClimatico.setPrecipitacionMm(new BigDecimal("2.30"));
            datoClimatico.setVelocidadVientoKmh(new BigDecimal("12.50"));
            datoClimatico.setIndiceUv(new BigDecimal("7.5"));
            datoClimatico.setCondicion("Parcialmente nublado");
            datoClimaticoRepository.save(datoClimatico);

            // TAREA AGRÍCOLA
            TareaAgricola tarea = new TareaAgricola();
            tarea.setParcela(parcela);
            tarea.setCultivo(cultivo);
            tarea.setUsuario(usuario);
            tarea.setTipoTarea("RIEGO");
            tarea.setDescripcion("Realizar riego por goteo en la parcela.");
            tarea.setFechaProgramada(LocalDate.now().plusDays(1));
            tarea.setEstado("PENDIENTE");
            tarea.setPrioridad("ALTA");
            tarea.setCostoEstimado(new BigDecimal("80.00"));
            tareaAgricolaRepository.save(tarea);

            // COSECHA
            Cosecha cosecha = new Cosecha();
            cosecha.setCultivo(cultivo);
            cosecha.setFechaCosecha(LocalDate.now().plusMonths(3));
            cosecha.setCantidadKg(new BigDecimal("1200.00"));
            cosecha.setCantidadDescarteKg(new BigDecimal("50.00"));
            cosecha.setCalidad("BUENA");
            cosecha.setMetodoCosecha("MANUAL");
            cosecha.setPrecioVentaKg(new BigDecimal("2.80"));
            cosecha.setDestino("Mercado mayorista");
            cosecha.setObservaciones("Cosecha estimada para prueba del sistema.");
            cosechaRepository.save(cosecha);

            System.out.println("Datos de prueba insertados correctamente.");
        };
    }
}