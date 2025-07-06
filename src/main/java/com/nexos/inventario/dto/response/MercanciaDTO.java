package com.nexos.inventario.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MercanciaDTO {
    private Long id;
    private String nombreProducto;
    private Integer cantidad;
    private LocalDate fechaIngreso;
    private UsuarioDTO usuarioRegistro;
    private UsuarioDTO usuarioModificacion;
    private LocalDate fechaModificacion;
}
