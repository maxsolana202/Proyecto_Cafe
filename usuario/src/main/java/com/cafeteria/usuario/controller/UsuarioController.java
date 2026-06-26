package com.cafeteria.usuario.controller;

import com.cafeteria.usuario.dto.UsuarioRequestDTO;
import com.cafeteria.usuario.dto.UsuarioResponseDTO;
import com.cafeteria.usuario.service.UsuarioFakerService;
import com.cafeteria.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioFakerService usuarioFakerService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(usuario -> {
                    // Agrega un link a sí mismo y otro a la lista completa
                    usuario.add(linkTo(methodOn(UsuarioController.class).obtenerPorId(id)).withSelfRel());
                    usuario.add(linkTo(methodOn(UsuarioController.class).obtenerTodos()).withRel("todos"));
                    return ResponseEntity.ok(usuario);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorEmail(@PathVariable String email) {
        return usuarioService.obtenerPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(201).body(usuarioService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return usuarioService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<UsuarioResponseDTO> desactivar(@PathVariable Long id) {
        return usuarioService.desactivar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (usuarioService.obtenerPorId(id).isEmpty())
            return ResponseEntity.notFound().build();
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/usuarios/auth/usuarios
// Llama al auth-service y trae sus usuarios
    @GetMapping("/auth/usuarios")
    public ResponseEntity<Object> obtenerUsuariosAuth() {
        return ResponseEntity.ok(usuarioService.obtenerUsuariosAuth());
    }
    @GetMapping("/generar-falsos/{cantidad}")
    public ResponseEntity<List<UsuarioResponseDTO>> generarFalsos(@PathVariable int cantidad) {
        return ResponseEntity.ok(usuarioFakerService.generarUsuariosFalsos(cantidad));
    }

}