package com.nexos.inventario.service.impl;

import com.nexos.inventario.dto.response.CargoDTO;
import com.nexos.inventario.dto.response.UsuarioDTO;
import com.nexos.inventario.dto.resquest.CrearUsuarioDTO;
import com.nexos.inventario.exception.custom.CargoNotFoundException;
import com.nexos.inventario.exception.custom.UsuarioNotFoundException;
import com.nexos.inventario.model.entity.Cargo;
import com.nexos.inventario.model.entity.Usuario;
import com.nexos.inventario.repository.CargoRepository;
import com.nexos.inventario.repository.UsuarioRepository;
import com.nexos.inventario.service.IUsuarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final CargoRepository cargoRepository;

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con el codigo: " + id));
        return toDTO(usuario);
    }

    @Override
    public UsuarioDTO crearUsuario(CrearUsuarioDTO dto) {
        if (dto.getCargoId() == null) {
            throw new IllegalArgumentException("El cargo no puede estar vació");
        }
        Cargo cargo = cargoRepository.findById(dto.getCargoId())
                .orElseThrow(() -> new CargoNotFoundException("No es posible registrar el usuario. Cargo no encontrado"));
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEdad(dto.getEdad());
        usuario.setCargo(cargo);
        usuario.setFechaIngreso(dto.getFechaIngreso());
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return toDTO(usuarioGuardado);
    }

    // Para evitar la duplicidad del builder
    private UsuarioDTO toDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .edad(usuario.getEdad())
                .cargo(CargoDTO.builder()
                        .nombre(usuario.getCargo().getNombre())
                        .build())
                .fechaIngreso(usuario.getFechaIngreso())
                .build();
    }


}
