package com.upc.smartharvest.Exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> manejarRuntimeException(RuntimeException ex) {
        String mensaje = ex.getMessage() != null ? ex.getMessage() : "Error en la solicitud";

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Bad Request";

        if (mensaje.toLowerCase().contains("no encontrado")
                || mensaje.toLowerCase().contains("no encontrada")) {
            status = HttpStatus.NOT_FOUND;
            error = "Not Found";
        }

        if (mensaje.toLowerCase().contains("credenciales")
                || mensaje.toLowerCase().contains("contraseña incorrecta")
                || mensaje.toLowerCase().contains("usuario o contraseña")) {
            status = HttpStatus.UNAUTHORIZED;
            error = "Unauthorized";
        }

        if (mensaje.toLowerCase().contains("inactivo")
                || mensaje.toLowerCase().contains("sin permisos")
                || mensaje.toLowerCase().contains("acceso denegado")) {
            status = HttpStatus.FORBIDDEN;
            error = "Forbidden";
        }

        return new ResponseEntity<>(crearError(status.value(), error, mensaje), status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                crearError(400, "Bad Request", "El cuerpo de la solicitud no tiene un formato JSON válido"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> manejarMetodoNoPermitido(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(
                crearError(405, "Method Not Allowed", "Método HTTP no permitido para este endpoint"),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> manejarParametroInvalido(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(
                crearError(400, "Bad Request", "El parámetro enviado no tiene el formato esperado"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> manejarErrorBaseDatos(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(
                crearError(400, "Bad Request", "No se puede guardar el registro porque incumple una restricción de la base de datos"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErrorGeneral(Exception ex) {
        return new ResponseEntity<>(
                crearError(500, "Internal Server Error", "Ocurrió un error interno en el sistema"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private Map<String, Object> crearError(int status, String error, String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status);
        response.put("error", error);
        response.put("message", message);
        return response;
    }
}