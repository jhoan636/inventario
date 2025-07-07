package com.nexos.inventario.service;

import com.nexos.inventario.dto.response.UsuarioDTO;
import com.nexos.inventario.dto.resquest.CrearUsuarioDTO;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioDTO> listarUsuarios();

    UsuarioDTO obtenerUsuarioPorId(Long id);

    UsuarioDTO crearUsuario(CrearUsuarioDTO dto);
}
