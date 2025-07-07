package com.nexos.inventario.exception;


import com.nexos.inventario.dto.response.ApiError;
import com.nexos.inventario.exception.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidacion(MethodArgumentNotValidException ex, HttpServletRequest req) {
        // Puedes personalizar este mensaje como desees
        String mensaje = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("Error de validación");

        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(mensaje)
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler({MercanciaYaExisteException.class, CargoYaExisteException.class})
    public ResponseEntity<ApiError> handleExiste(RuntimeException ex, HttpServletRequest req) {
        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler({MercanciaNotFoundException.class, UsuarioNotFoundException.class, CargoNotFoundException.class})
    public ResponseEntity<ApiError> handleNoEncontrado(RuntimeException ex, HttpServletRequest req) {
        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(AccesoNoAutorizadoException.class)
    public ResponseEntity<ApiError> handleAccesoDenegado(AccesoNoAutorizadoException ex, HttpServletRequest req) {
        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleInesperado(HttpServletRequest req) {
        ApiError err = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message("Ha ocurrido un error inesperado.")
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}
