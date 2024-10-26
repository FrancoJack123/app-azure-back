package com.cibertec.edu.controller;

import com.cibertec.edu.dto.LoginRequestDTO;
import com.cibertec.edu.dto.LoginResponseDTO;
import com.cibertec.edu.dto.UsuarioDTO;
import com.cibertec.edu.service.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class AutenticacionController {

    @Autowired
    private AutenticacionService autenticacionService;

    @PostMapping("/autenticar")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            UsuarioDTO usuario = autenticacionService.validarUsuario(loginRequestDTO);
            if (usuario != null) {
                return new LoginResponseDTO(usuario, "Login exitoso2");
            } else {
                return new LoginResponseDTO(null, "Problemas en la autenticación");
            }
        } catch (IOException e) {
            return new LoginResponseDTO(null, "Error en el sistema: " + e.getMessage());
        }
    }

    @GetMapping("/get-integrantes")
    public List<UsuarioDTO> listarUsuarios() {
        try {
            return autenticacionService.listarUsuario();
        } catch (IOException e) {
            throw new RuntimeException("Error al listar usuarios: " + e.getMessage());
        }
    }
}
