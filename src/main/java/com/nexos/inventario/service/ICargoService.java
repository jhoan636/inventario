package com.nexos.inventario.service;

import com.nexos.inventario.dto.response.CargoDTO;

import java.util.List;

public interface ICargoService {
    List<CargoDTO> obtenerTodosLosCargos();

    CargoDTO crearCargo(String nombre);
}
