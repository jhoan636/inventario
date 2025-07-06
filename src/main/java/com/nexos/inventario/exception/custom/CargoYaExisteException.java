package com.nexos.inventario.exception.custom;

public class CargoYaExisteException extends RuntimeException {
    public CargoYaExisteException(String message) {
        super(message);
    }
}
