package com.cafeteria.menu.controller;

import com.cafeteria.menu.dto.ProductoRequestDTO;
import com.cafeteria.menu.dto.ProductoResponseDTO;
import com.cafeteria.menu.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerDisponibles() {
        return ResponseEntity.ok(productoService.obtenerDisponibles());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorCategoria(
            @PathVariable String categoria) {
        return ResponseEntity.ok(productoService.obtenerPorCategoria(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(
            @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.status(201).body(productoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO dto) {
        return productoService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<ProductoResponseDTO> desactivar(@PathVariable Long id) {
        return productoService.desactivar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productoService.obtenerPorId(id).isEmpty())
            return ResponseEntity.notFound().build();
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}