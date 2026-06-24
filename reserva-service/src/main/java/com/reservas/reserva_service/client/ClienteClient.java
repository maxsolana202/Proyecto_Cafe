package com.reservas.reserva_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "cliente-service")
public interface ClienteClient {

    @GetMapping("/clientes")
    String obtenerClientes();

}