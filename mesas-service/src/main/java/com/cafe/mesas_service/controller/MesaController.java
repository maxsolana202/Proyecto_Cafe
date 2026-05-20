package com.cafe.mesas_service.controller;

import com.cafe.mesas_service.model.Mesa;
import com.cafe.mesas_service.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @PostMapping
    public ResponseEntity<Mesa> crear(@Valid @RequestBody Mesa mesa) {
        return new ResponseEntity<>(mesaService.guardar(mesa), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Mesa>> listar() {
        return ResponseEntity.ok(mesaService.listarTodas());
    }
}