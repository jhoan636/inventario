package com.nexos.inventario.controller;

import com.nexos.inventario.dto.response.CargoDTO;
import com.nexos.inventario.dto.resquest.CrearCargoDTO;
import com.nexos.inventario.service.CargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CargoController {

    private final CargoService cargoService;

    @GetMapping
    public ResponseEntity<List<CargoDTO>> listRoles() {
        System.out.println("hola");
        try {
            return ResponseEntity.ok(cargoService.obtenerTodosLosCargos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<CargoDTO> createRole(@Valid @RequestBody CrearCargoDTO dto) {

        CargoDTO created = cargoService.crearCargo(dto.getNombre());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);

    }
}
