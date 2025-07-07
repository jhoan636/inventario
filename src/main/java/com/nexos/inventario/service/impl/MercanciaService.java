package com.nexos.inventario.service.impl;

import com.nexos.inventario.dto.response.MercanciaDTO;
import com.nexos.inventario.dto.response.UsuarioDTO;
import com.nexos.inventario.dto.resquest.ActualizarMercanciaDTO;
import com.nexos.inventario.dto.resquest.CrearMercanciaDTO;
import com.nexos.inventario.exception.custom.MercanciaNotFoundException;
import com.nexos.inventario.exception.custom.MercanciaYaExisteException;
import com.nexos.inventario.exception.custom.UsuarioNotFoundException;
import com.nexos.inventario.model.entity.Mercancia;
import com.nexos.inventario.model.entity.Usuario;
import com.nexos.inventario.repository.MercanciaRepository;
import com.nexos.inventario.repository.UsuarioRepository;
import com.nexos.inventario.service.IMercanciaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MercanciaService implements IMercanciaService {

    private final MercanciaRepository mercanciaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<MercanciaDTO> listarMercancias() {
        List<Mercancia> mercancias = mercanciaRepository.findAll();
        return mercancias.stream()
                .filter(Mercancia::getActivo)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MercanciaDTO obtenerMercanciaPorId(Long id) {
        Mercancia mercancia = mercanciaRepository.findById(id).filter(Mercancia::getActivo)
                .orElseThrow(() -> new MercanciaNotFoundException("Mercancía no encontrada con el codigo: " + id));
        return toDTO(mercancia);
    }

    @Override
    public MercanciaDTO crearMercancia(CrearMercanciaDTO crearMercanciaDTO) {
        if (mercanciaRepository.findByNombre(crearMercanciaDTO.getNombre()).isPresent()) {
            throw new MercanciaYaExisteException("Ya existe una mercancía con el nombre: " + crearMercanciaDTO.getNombre());
        }
        Usuario usuarioRegistro = usuarioRepository.findById(crearMercanciaDTO.getUsuarioRegistroId())
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario no fue encontrado."));
        Mercancia mercancia = new Mercancia();
        mercancia.setNombre(crearMercanciaDTO.getNombre());
        mercancia.setCantidad(crearMercanciaDTO.getCantidad());
        mercancia.setFechaIngreso(crearMercanciaDTO.getFechaIngreso());
        mercancia.setUsuarioRegistro(usuarioRegistro);
        return toDTO(mercanciaRepository.save(mercancia));
    }

    @Override
    public MercanciaDTO actualizarMercancia(Long id, ActualizarMercanciaDTO actualizarMercanciaDTO) {
        if (mercanciaRepository.findByNombre(actualizarMercanciaDTO.getNombre()).isPresent()) {
            throw new MercanciaYaExisteException("Ya existe una mercancía con el nombre: " + actualizarMercanciaDTO.getNombre());
        }

        Mercancia mercanciaExistente = mercanciaRepository.findById(id)
                .orElseThrow(() -> new MercanciaNotFoundException("Mercancía no encontrada con el codigo: " + id));

        Usuario usuarioModificacion = usuarioRepository.findById(actualizarMercanciaDTO.getUsuarioModificacionId())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con el codigo: " + actualizarMercanciaDTO.getUsuarioModificacionId()));

        mercanciaExistente.setNombre(actualizarMercanciaDTO.getNombre());
        mercanciaExistente.setCantidad(actualizarMercanciaDTO.getCantidad());
        mercanciaExistente.setUsuarioModificacion(usuarioModificacion);
        mercanciaExistente.setFechaModificacion(LocalDateTime.now());
        return toDTO(mercanciaRepository.save(mercanciaExistente));
    }

    @Override
    public void eliminarMercancia(Long id, Long usuarioId) {
        Mercancia mercancia = mercanciaRepository.findById(id)
                .orElseThrow(() -> new MercanciaNotFoundException("Mercancía no encontrada con el codigo: " + id));
        Usuario usuarioModificacion = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con el codigo: " + usuarioId));
        mercancia.setActivo(false);
        mercancia.setUsuarioModificacion(usuarioModificacion);
        mercancia.setFechaModificacion(LocalDateTime.now());
        // Generamos un guardado logico
        mercanciaRepository.save(mercancia);
    }

    @Override
    public List<MercanciaDTO> buscarMercanciaConFiltros(LocalDate fechaIngreso, String usuarioNombre, String nombreProducto) {
        if (fechaIngreso == null &&
                (usuarioNombre == null || usuarioNombre.trim().isEmpty()) &&
                (nombreProducto == null || nombreProducto.trim().isEmpty())) {
            throw new IllegalArgumentException("Debe especificar al menos un filtro de búsqueda");
        }
        List<Mercancia> mercancias = mercanciaRepository.buscarConFiltros(fechaIngreso, usuarioNombre, nombreProducto);
        return mercancias.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private MercanciaDTO toDTO(Mercancia mercancia) {
        return MercanciaDTO.builder()
                .id(mercancia.getId())
                .nombre(mercancia.getNombre())
                .cantidad(mercancia.getCantidad())
                .fechaIngreso(mercancia.getFechaIngreso())
                .usuarioRegistro(UsuarioDTO.builder()
                        .nombre(mercancia.getUsuarioRegistro().getNombre()).build())
                .usuarioModificacion(
                        mercancia.getUsuarioModificacion() != null
                                ? UsuarioDTO.builder()
                                .nombre(mercancia.getUsuarioModificacion().getNombre())
                                .build()
                                : null)
                .fechaModificacion(mercancia.getFechaModificacion())
                .build();
    }
}
