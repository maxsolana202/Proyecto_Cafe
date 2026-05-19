package com.cafeteria.menu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "menu", url = "http://localhost:8082")
public interface MenuClient {

    @GetMapping("/api/productos/{id}")
    Object obtenerProductoPorId(@PathVariable Long id);

    @GetMapping("/api/productos")
    Object obtenerTodosLosProductos();
}