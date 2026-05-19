package com.cafeteria.usuario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pedido", url = "http://localhost:8083")
public interface UsuarioClient {

    @GetMapping("/api/pedido/{id}")
    Object obtenerPedidoPorId(@PathVariable Long id);
}