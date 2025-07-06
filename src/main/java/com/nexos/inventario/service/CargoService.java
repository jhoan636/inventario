package com.nexos.inventario.service;

import com.nexos.inventario.dto.response.CargoDTO;
import com.nexos.inventario.exception.custom.CargoYaExisteException;
import com.nexos.inventario.model.entity.Cargo;
import com.nexos.inventario.repository.CargoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;

    public List<CargoDTO> obtenerTodosLosCargos() {
        List<Cargo> cargos = cargoRepository.findAll();
        return cargos.stream().map(c -> CargoDTO.builder()
                        .id(c.getId())
                        .nombre(c.getNombre())
                        .build())
                .collect(Collectors.toList());
    }

    public CargoDTO crearCargo(String nombre) {
        Cargo cargo = cargoRepository.save(new Cargo(nombre));
        if (cargoRepository.findByNombre(nombre).isPresent()) {
            throw new CargoYaExisteException("El cargo ya existe con el nombre: " + nombre);
        }
        return CargoDTO.builder().id(cargo.getId()).nombre(cargo.getNombre()).build();
    }

    // Se usa para hacer un precarge de los datos
//    @PostConstruct
//    public void inicializarCargos() {
//        if (cargoRepository.count() == 0) {
//            crearCargo("Asesor de ventas");
//            crearCargo("Administrador");
//            crearCargo("Soporte");
//        }
//    }
}
