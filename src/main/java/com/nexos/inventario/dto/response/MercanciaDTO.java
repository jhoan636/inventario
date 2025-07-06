package com.nexos.inventario.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MercanciaDTO {
    private Long id;
    private String nombre;
    private Integer cantidad;
    private LocalDate fechaIngreso;
    private LocalDate fechaModificacion;
    private UsuarioDTO creador;
    private UsuarioDTO modificador;
}
