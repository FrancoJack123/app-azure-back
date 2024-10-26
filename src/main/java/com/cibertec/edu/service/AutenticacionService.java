package com.cibertec.edu.service;

import com.cibertec.edu.dto.LoginRequestDTO;
import com.cibertec.edu.dto.UsuarioDTO;

import java.io.IOException;
import java.util.List;

public interface AutenticacionService {
    UsuarioDTO validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException;
    List<UsuarioDTO> listarUsuario() throws IOException;
}
