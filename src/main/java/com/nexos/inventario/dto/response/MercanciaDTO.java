package com.nexos.inventario.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class MercanciaDTO {
    private Long id;
    private String nombre;
    private Integer cantidad;
    private LocalDate fechaIngreso;
    private UsuarioDTO usuarioRegistro;
    private UsuarioDTO usuarioModificacion;
    private LocalDateTime fechaModificacion;
}
