package com.nexos.inventario.exception.custom;

public class AccesoNoAutorizadoException extends RuntimeException {
    public AccesoNoAutorizadoException(String message) {
        super(message);
    }
}
