package com.example.categoria.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> manejarResourceNotFoundException(
            ResourceNotFoundException ex,
            WebRequest request) {

        log.warn("Recurso no encontrado: {}", ex.getMessage());

        Map<String, Object> cuerpoError = construirCuerpoError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(cuerpoError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> manejarIllegalArgumentException(
            IllegalArgumentException ex,
            WebRequest request) {

        log.warn("Argumento inválido: {}", ex.getMessage());

        Map<String, Object> cuerpoError = construirCuerpoError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(cuerpoError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarExcepcionGeneral(
            Exception ex,
            WebRequest request) {

        log.error("Error interno del servidor: ", ex);

        String mensajeCliente = "Ha ocurrido un error interno en el servidor.";

        Map<String, Object> cuerpoError = construirCuerpoError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                mensajeCliente,
                request.getDescription(false)
        );

        return new ResponseEntity<>(cuerpoError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> construirCuerpoError(
            HttpStatus estado,
            String mensaje,
            String ruta) {

        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("timestamp", LocalDateTime.now());
        cuerpo.put("estado", estado.value());
        cuerpo.put("error", estado.getReasonPhrase());
        cuerpo.put("mensaje", mensaje);
        cuerpo.put("ruta", ruta.replace("uri=", ""));

        return cuerpo;
    }
}
