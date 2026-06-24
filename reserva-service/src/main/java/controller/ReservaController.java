package com.reservas.reserva_service.controller;

import com.reservas.reserva_service.entity.Reserva;
import com.reservas.reserva_service.hateoas.ReservaModelAssembler;
import com.reservas.reserva_service.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaModelAssembler assembler;

    // Crear reserva
    @PostMapping
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservaService.guardarReserva(reserva);
    }

    // Obtener todas las reservas con HATEOAS
    @GetMapping
    public CollectionModel<EntityModel<Reserva>> listarReservas() {

        List<EntityModel<Reserva>> reservas = reservaService
                .listarReservas()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(reservas);
    }

    // Obtener reserva por ID con HATEOAS
    @GetMapping("/{id}")
    public EntityModel<Reserva> obtenerReserva(@PathVariable Long id) {

        Reserva reserva = reservaService.obtenerReservaPorId(id);

        return assembler.toModel(reserva);
    }

    // Eliminar reserva
    @DeleteMapping("/{id}")
    public String eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return "Reserva eliminada correctamente";
    }
}