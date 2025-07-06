package com.nexos.inventario.repository;

import com.nexos.inventario.model.entity.Mercancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MercanciaRepository extends JpaRepository<Mercancia, Long> {
    Optional<Mercancia> findByNombreProducto(String nombreProducto);

    Optional<Mercancia> findByNombreProductoAndIdNot(String nombreProducto, Long id);

    @Query("SELECT m FROM Mercancia m WHERE " +
            "(:fechaIngreso IS NULL OR m.fechaIngreso = :fechaIngreso) AND " +
            "(:usuarioNombre IS NULL OR m.usuarioRegistro.nombre LIKE %:usuarioNombre%) AND " +
            "(:nombreProducto  IS NULL OR m.nombre LIKE %:nombreProducto%)")
    List<Mercancia> buscarConFiltros(@Param("fechaIngreso") LocalDate fechaIngreso,
                                     @Param("usuarioNombre") String usuarioNombre,
                                     @Param("nombreProducto") String nombreProducto);
}
