package com.nexos.inventario.dto.resquest;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CrearMercanciaDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre del producto no puede exceder 100 caracteres")
    private String nombre;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @PastOrPresent(message = "La fecha de ingreso no puede ser mayor a la fecha actual")
    private LocalDate fechaIngreso;

    @NotNull(message = "El usuario asociado al producto es obligatoria")
    private Long usuarioRegistroId;
}
