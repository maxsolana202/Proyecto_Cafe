package com.cafe.auth_service.controller;

import com.cafe.auth_service.model.Usuario;
import com.cafe.auth_service.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación y Usuarios", description = "API para gestionar usuarios y accesos al sistema")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario en el sistema")
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
        agregarEnlacesHateoas(nuevoUsuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Valida credenciales y retorna un token de acceso")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String token = usuarioService.login(username, password);
        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    @GetMapping("/usuarios")
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios registrados")
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        usuarios.forEach(this::agregarEnlacesHateoas);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Busca un usuario específico")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerPorId(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            agregarEnlacesHateoas(usuario);
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/usuarios/{id}")
    @Operation(summary = "Eliminar usuario", description = "Borra un usuario del sistema")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private void agregarEnlacesHateoas(Usuario usuario) {
        usuario.removeLinks();
        usuario.add(linkTo(methodOn(AuthController.class).obtenerPorId(usuario.getId())).withSelfRel());
        usuario.add(linkTo(methodOn(AuthController.class).listar()).withRel("todos-los-usuarios"));
    }
}