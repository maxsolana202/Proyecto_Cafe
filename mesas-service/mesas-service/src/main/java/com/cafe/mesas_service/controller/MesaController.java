package com.cafe.mesas_service.controller;

import com.cafe.mesas_service.model.Mesa;
import com.cafe.mesas_service.service.MesaService;
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
@RequestMapping("/api/mesas")
@Tag(name = "Mesas", description = "API para gestionar las mesas del café")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @PostMapping
    @Operation(summary = "Crear mesa", description = "Registra una nueva mesa")
    public ResponseEntity<Mesa> crear(@Valid @RequestBody Mesa mesa) {
        Mesa nuevaMesa = mesaService.guardar(mesa);
        agregarEnlaces(nuevaMesa);
        return new ResponseEntity<>(nuevaMesa, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar mesas", description = "Obtiene todas las mesas")
    public ResponseEntity<List<Mesa>> listar() {
        List<Mesa> mesas = mesaService.listar();
        mesas.forEach(this::agregarEnlaces);
        return ResponseEntity.ok(mesas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener mesa por ID", description = "Busca una mesa específica")
    public ResponseEntity<Mesa> obtenerPorId(@PathVariable Long id) {
        Optional<Mesa> mesaOpt = mesaService.obtenerPorId(id);
        if (mesaOpt.isPresent()) {
            Mesa mesa = mesaOpt.get();
            agregarEnlaces(mesa);
            return ResponseEntity.ok(mesa);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar mesa", description = "Modifica los datos de una mesa")
    public ResponseEntity<Mesa> actualizar(@PathVariable Long id, @Valid @RequestBody Mesa mesa) {
        if (mesaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mesa.setId(id);
        Mesa actualizada = mesaService.guardar(mesa);
        agregarEnlaces(actualizada);
        return ResponseEntity.ok(actualizada);
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado", description = "Actualiza el estado de una mesa: LIBRE, OCUPADA o RESERVADA")
    public ResponseEntity<Mesa> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        Optional<Mesa> mesaOpt = mesaService.obtenerPorId(id);
        if (mesaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Mesa mesa = mesaOpt.get();
        mesa.setEstado(estado);
        Mesa actualizada = mesaService.guardar(mesa);
        agregarEnlaces(actualizada);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar mesa", description = "Borra una mesa del sistema")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mesaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private void agregarEnlaces(Mesa mesa) {
        mesa.removeLinks();
        mesa.add(linkTo(methodOn(MesaController.class).obtenerPorId(mesa.getId())).withSelfRel());
        mesa.add(linkTo(methodOn(MesaController.class).listar()).withRel("todas-las-mesas"));
    }
}
