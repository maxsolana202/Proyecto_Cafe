package com.reservas.reserva_service.controller;

import com.reservas.reserva_service.client.ClienteClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestFeignController {

    private final ClienteClient clienteClient;

    public TestFeignController(ClienteClient clienteClient) {
        this.clienteClient = clienteClient;
    }

    @GetMapping("/test-feign")
    public String probarFeign() {
        return clienteClient.obtenerClientes();
    }

}