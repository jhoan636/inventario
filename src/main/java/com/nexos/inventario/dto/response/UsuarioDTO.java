package com.nexos.inventario.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private Integer edad;
    private LocalDate fechaIngreso;
    private CargoDTO cargo;
}
