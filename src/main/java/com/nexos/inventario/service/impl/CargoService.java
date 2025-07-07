package com.nexos.inventario.service.impl;

import com.nexos.inventario.dto.response.CargoDTO;
import com.nexos.inventario.exception.custom.CargoYaExisteException;
import com.nexos.inventario.model.entity.Cargo;
import com.nexos.inventario.repository.CargoRepository;
import com.nexos.inventario.service.ICargoService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CargoService implements ICargoService {

    private final CargoRepository cargoRepository;

    @Override
    public List<CargoDTO> obtenerTodosLosCargos() {
        List<Cargo> cargos = cargoRepository.findAll();
        return cargos.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CargoDTO crearCargo(String nombre) {
        if (cargoRepository.findByNombre(nombre).isPresent()) {
            throw new CargoYaExisteException("El cargo ya existe con el nombre: " + nombre);
        }
        Cargo cargo = cargoRepository.save(new Cargo(nombre));
        return toDTO(cargo);
    }

    public CargoDTO toDTO(Cargo cargo) {
        return CargoDTO.builder()
                .id(cargo.getId())
                .nombre(cargo.getNombre())
                .build();
    }

    // Se usa para hacer un precarge de los datos
    @PostConstruct
    public void inicializarCargos() {
        if (cargoRepository.count() == 0) {
            crearCargo("Asesor de ventas");
            crearCargo("Administrador");
            crearCargo("Soporte");
        }
    }
}
