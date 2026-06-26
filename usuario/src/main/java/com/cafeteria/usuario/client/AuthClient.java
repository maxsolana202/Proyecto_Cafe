package com.cafeteria.usuario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "http://localhost:8083")
public interface AuthClient {

    @PostMapping("/api/auth/registro")
    Object registrar(@RequestBody Object usuario);

    @GetMapping("/api/auth/usuarios")
    Object listarUsuarios();
}