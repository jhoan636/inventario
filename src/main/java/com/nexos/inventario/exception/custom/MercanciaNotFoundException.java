package com.nexos.inventario.exception.custom;

public class MercanciaNotFoundException extends RuntimeException {
    public MercanciaNotFoundException(String message) {
        super(message);
    }
}
