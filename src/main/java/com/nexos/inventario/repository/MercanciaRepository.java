package com.nexos.inventario.repository;

import com.nexos.inventario.model.entity.Mercancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MercanciaRepository extends JpaRepository<Mercancia, Long> {
    Optional<Mercancia> findByNombre(String nombre);
    
    @Query(value = "SELECT m.* FROM mercancia m " +
            "LEFT JOIN usuarios ur ON m.usuario_registro_id = ur.id " +
            "LEFT JOIN usuarios um ON m.usuario_modificacion_id = um.id " +
            "WHERE (CAST(:fechaIngreso AS DATE) IS NULL OR m.fecha_ingreso = CAST(:fechaIngreso AS DATE))" +
            "AND (:usuarioNombre IS NULL OR " +
            "     ur.nombre ILIKE CONCAT('%', :usuarioNombre, '%') OR " +
            "     um.nombre ILIKE CONCAT('%', :usuarioNombre, '%')) " +
            "AND (:nombreProducto IS NULL OR m.nombre ILIKE CONCAT('%', :nombreProducto, '%'))",
            nativeQuery = true)
    List<Mercancia> buscarConFiltros(@Param("fechaIngreso") LocalDate fechaIngreso,
                                     @Param("usuarioNombre") String usuarioNombre,
                                     @Param("nombreProducto") String nombreProducto);
}
