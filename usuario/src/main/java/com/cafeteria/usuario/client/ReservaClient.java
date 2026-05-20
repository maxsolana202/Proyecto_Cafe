package com.cafeteria.usuario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "reserva-service", url = "http://localhost:8081")
public interface ReservaClient {

    @GetMapping("/reservas")
    Object listarReservas();

    @GetMapping("/reservas/{id}")
    Object obtenerReserva(@PathVariable Long id);

    @PostMapping("/reservas")
    Object crearReserva(@RequestBody Object reserva);

    @DeleteMapping("/reservas/{id}")
    String eliminarReserva(@PathVariable Long id);
}