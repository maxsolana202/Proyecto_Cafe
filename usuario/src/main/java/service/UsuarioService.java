package com.cafeteria.usuario.service;

import com.cafeteria.usuario.dto.UsuarioRequestDTO;
import com.cafeteria.usuario.dto.UsuarioResponseDTO;
import com.cafeteria.usuario.model.Usuario;
import com.cafeteria.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private UsuarioResponseDTO mapToDTO(Usuario u) {
        return new UsuarioResponseDTO(
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                u.getTelefono(),
                u.getDireccion(),
                u.getRol(),
                u.getActivo()
        );
    }

    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {
        return usuarioRepository.findById(id).map(this::mapToDTO);
    }

    public Optional<UsuarioResponseDTO> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email).map(this::mapToDTO);
    }

    public UsuarioResponseDTO guardar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }
        Usuario usuario = new Usuario(
                null,
                dto.getNombre(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getTelefono(),
                dto.getDireccion(),
                dto.getRol(),
                true
        );
        return mapToDTO(usuarioRepository.save(usuario));
    }

    public Optional<UsuarioResponseDTO> actualizar(Long id, UsuarioRequestDTO dto) {
        return usuarioRepository.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setEmail(dto.getEmail());
            existente.setPassword(dto.getPassword());
            existente.setTelefono(dto.getTelefono());
            existente.setDireccion(dto.getDireccion());
            existente.setRol(dto.getRol());
            return mapToDTO(usuarioRepository.save(existente));
        });
    }

    public Optional<UsuarioResponseDTO> desactivar(Long id) {
        return usuarioRepository.findById(id).map(existente -> {
            existente.setActivo(false);
            return mapToDTO(usuarioRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}