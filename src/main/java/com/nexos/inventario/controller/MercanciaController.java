package com.nexos.inventario.controller;

import com.nexos.inventario.dto.response.MercanciaDTO;
import com.nexos.inventario.dto.resquest.ActualizarMercanciaDTO;
import com.nexos.inventario.dto.resquest.CrearMercanciaDTO;
import com.nexos.inventario.service.IMercanciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/mercancias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MercanciaController {

    private final IMercanciaService mercanciaService;

    @GetMapping
    public ResponseEntity<List<MercanciaDTO>> listarMercancias() {
        List<MercanciaDTO> mercancias = mercanciaService.listarMercancias();
        return ResponseEntity.ok(mercancias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MercanciaDTO> obtenerMercanciaPorId(@PathVariable Long id) {
        MercanciaDTO mercancia = mercanciaService.obtenerMercanciaPorId(id);
        return ResponseEntity.ok(mercancia);
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarMercanciaConFiltros(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaIngreso,
            @RequestParam(required = false) String usuarioNombre,
            @RequestParam(required = false) String nombreProducto) {

        List<MercanciaDTO> mercancias = mercanciaService.buscarMercanciaConFiltros(fechaIngreso, usuarioNombre, nombreProducto);
        return ResponseEntity.ok(mercancias);
    }

    @PostMapping
    public ResponseEntity<MercanciaDTO> crearMercancia(@Valid @RequestBody CrearMercanciaDTO dto) {
        MercanciaDTO created = mercanciaService.crearMercancia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MercanciaDTO> actualizarMercancia(@PathVariable Long id, @Valid @RequestBody ActualizarMercanciaDTO dto) {
        MercanciaDTO updated = mercanciaService.actualizarMercancia(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMercancia(@PathVariable Long id, @RequestParam("usuarioId") Long usuarioId) {
        mercanciaService.eliminarMercancia(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

}
