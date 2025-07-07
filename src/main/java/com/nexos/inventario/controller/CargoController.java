package com.nexos.inventario.controller;

import com.nexos.inventario.dto.response.CargoDTO;
import com.nexos.inventario.dto.resquest.CrearCargoDTO;
import com.nexos.inventario.service.ICargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CargoController {

    private final ICargoService cargoService;

    @GetMapping
    public ResponseEntity<List<CargoDTO>> listCargos() {
        return ResponseEntity.ok(cargoService.obtenerTodosLosCargos());
    }

    @PostMapping
    public ResponseEntity<CargoDTO> CrearCargo(@Valid @RequestBody CrearCargoDTO dto) {
        CargoDTO created = cargoService.crearCargo(dto.getNombre());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }
}
