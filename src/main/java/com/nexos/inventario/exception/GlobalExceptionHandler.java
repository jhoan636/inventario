package com.nexos.inventario.exception;


import com.nexos.inventario.dto.response.ApiError;
import com.nexos.inventario.exception.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(DataIntegrityViolationException ex, HttpServletRequest req) {
        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Conflict")
                .message("Violación de restricción de unicidad: " +
                        extractConstraintMessage(ex))
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(MercanciaYaExisteException.class)
    public ResponseEntity<ApiError> handleMercanciaExists(
            MercanciaYaExisteException ex,
            HttpServletRequest req
    ) {
        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Conflict")
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(CargoYaExisteException.class)
    public ResponseEntity<ApiError> handleCargoExists(CargoYaExisteException ex, HttpServletRequest req) {
        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Conflict")
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }


    @ExceptionHandler({
            MercanciaNotFoundException.class,
            UsuarioNotFoundException.class
    })
    public ResponseEntity<ApiError> handleNotFound(
            RuntimeException ex,
            HttpServletRequest req
    ) {
        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(AccesoNoAutorizadoException.class)
    public ResponseEntity<ApiError> handleAccessDenied(
            AccesoNoAutorizadoException ex,
            HttpServletRequest req
    ) {
        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error("Forbidden")
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllOthers(
            Exception ex,
            HttpServletRequest req
    ) {
        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("Ha ocurrido un error inesperado.")
                .path(req.getRequestURI())
                .build();
        // opcional: log.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    /**
     * Extrae un fragmento legible de la excepción de constraint, p.ej. el valor duplicado
     */
    private String extractConstraintMessage(DataIntegrityViolationException ex) {
        String msg = ex.getRootCause() != null
                ? ex.getRootCause().getMessage()
                : ex.getMessage();
        // aquí podrías parsear msg para quedarte solo con el "(nombre)=(Administrador)"
        return msg;
    }
}
