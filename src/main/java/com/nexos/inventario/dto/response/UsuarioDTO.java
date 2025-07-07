package com.nexos.inventario.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private Integer edad;
    private CargoDTO cargo;
    private LocalDate fechaIngreso;
}
