package com.nexos.inventario.exception.custom;

public class MercanciaYaExisteException extends RuntimeException {
    public MercanciaYaExisteException(String message) {
        super(message);
    }
}
