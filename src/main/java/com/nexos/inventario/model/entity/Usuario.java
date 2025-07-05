package com.nexos.inventario.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer edad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "cargo_id",
            nullable = false
    )
    private Cargo cargo;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;
}
