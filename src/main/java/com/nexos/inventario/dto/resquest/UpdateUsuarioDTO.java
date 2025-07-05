package com.nexos.inventario.dto.resquest;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUsuarioDTO {
    @NotNull(message = "El id de usuario es obligatorio")
    private Long id;

    @NotBlank
    @Size(max = 100, message = "El nombre no puede estar vacío ni exceder 100 caracteres")
    private String nombre;

    @NotNull
    @Min(value = 18, message = "La edad mínima es 18")
    @Max(value = 100, message = "La edad máxima es 100")
    private Integer edad;

    @PastOrPresent(message = "La fecha del usuario no puede ser superior a la actual")
    private LocalDate fechaIngreso;

    @NotNull(message = "Debe especificar un cargo existente")
    private Long cargoId;
}
