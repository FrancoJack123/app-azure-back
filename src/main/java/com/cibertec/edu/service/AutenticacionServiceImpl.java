package com.cibertec.edu.service;

import com.cibertec.edu.dto.LoginRequestDTO;
import com.cibertec.edu.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public UsuarioDTO validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:usuarios.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {
            return br.lines()
                    .map(this::mapToUsuarioDTO)
                    .filter(usuario -> loginRequestDTO.codigoAlumno().equals(usuario.getCodigoAlumno()) &&
                            loginRequestDTO.password().equals(usuario.getPassword()))
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            throw new IOException("Error al validar usuario", e);
        }
    }

    @Override
    public List<UsuarioDTO> listarUsuario() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:usuarios.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {
            return br.lines()
                    .map(this::mapToUsuarioDTO)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Error al listar usuarios", e);
        }
    }

    private UsuarioDTO mapToUsuarioDTO(String linea) {
        String[] datos = linea.split(";");
        if (datos.length < 5) {
            throw new IllegalArgumentException("Formato de datos inválido en usuarios.txt");
        }
        return new UsuarioDTO(datos[0], datos[1], datos[2], datos[3], datos[4]);
    }
}
