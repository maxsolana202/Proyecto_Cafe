package com.cafeteria.menu.controller;

import com.cafeteria.menu.dto.ProductoRequestDTO;
import com.cafeteria.menu.dto.ProductoResponseDTO;
import com.cafeteria.menu.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API de gestión del Menú - Microservicio Menu")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar todos los productos", description = "Devuelve una lista completa con enlaces HATEOAS")
    public ResponseEntity<CollectionModel<ProductoResponseDTO>> obtenerTodos() {
        List<ProductoResponseDTO> productos = productoService.obtenerTodos();

        // Agregar link a cada producto individualmente
        productos.forEach(p ->
                p.add(linkTo(methodOn(ProductoController.class).obtenerPorId(p.getId())).withSelfRel())
        );

        // Crear modelo de colección con link al inicio
        CollectionModel<ProductoResponseDTO> model = CollectionModel.of(productos);
        model.add(linkTo(methodOn(ProductoController.class).obtenerTodos()).withSelfRel());

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerPorId(id) // Esto devuelve un Optional<ProductoResponseDTO>
                .map(dto -> {
                    // Si el producto existe, le agregamos los links
                    dto.add(linkTo(methodOn(ProductoController.class).obtenerPorId(id)).withSelfRel());
                    dto.add(linkTo(methodOn(ProductoController.class).obtenerTodos()).withRel("lista-productos"));

                    // Retornamos el DTO dentro de un ResponseEntity
                    return ResponseEntity.ok(dto);
                })
                // Si no existe, devolvemos un 404 Not Found
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo producto")
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        ProductoResponseDTO guardado = productoService.guardar(dto);
        guardado.add(linkTo(methodOn(ProductoController.class).obtenerPorId(guardado.getId())).withSelfRel());
        return ResponseEntity.status(201).body(guardado);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO dto) {
        // Si tu servicio devuelve Optional<ProductoResponseDTO>:
        return productoService.actualizar(id, dto)
                .map(actualizado -> {
                    actualizado.add(linkTo(methodOn(ProductoController.class).obtenerPorId(id)).withSelfRel());
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build()); // 404 si el ID no existía
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}