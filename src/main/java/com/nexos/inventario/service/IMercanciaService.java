package com.nexos.inventario.service;

import com.nexos.inventario.dto.response.MercanciaDTO;
import com.nexos.inventario.dto.resquest.ActualizarMercanciaDTO;
import com.nexos.inventario.dto.resquest.CrearMercanciaDTO;

import java.time.LocalDate;
import java.util.List;

public interface IMercanciaService {
    List<MercanciaDTO> listarMercancias();

    MercanciaDTO obtenerMercanciaPorId(Long id);

    MercanciaDTO crearMercancia(CrearMercanciaDTO crearMercanciaDTO);

    MercanciaDTO actualizarMercancia(Long id, ActualizarMercanciaDTO actualizarMercanciaDTO);

    void eliminarMercancia(Long id, Long usuarioId);

    List<MercanciaDTO> buscarMercanciaConFiltros(LocalDate fechaIngreso, String usuarioNombre, String nombreProducto);
}
