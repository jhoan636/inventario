package com.nexos.inventario.dto.resquest;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateMercanciaDTO {

    @NotNull
    private Long id;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 150)
    private String nombre;

    @NotNull(message = "El nombre del producto es obligatorio")
    @Min(0)
    private Integer cantidad;

    @NotNull(message = "La fecha de ingreso del producto es obligatoria")
    @PastOrPresent(message = "La fecha del producto no puede ser superior a la actual")
    private LocalDate fechaIngreso;

    @NotNull(message = "El usuario asociado al producto es obligatoria")
    private Long usuarioModificacionId;
}
