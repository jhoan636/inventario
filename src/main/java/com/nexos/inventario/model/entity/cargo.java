package com.nexos.inventario.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cargo")
@Data
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String nombre;
}
