package com.cafeteria.usuario.service;

import com.cafeteria.usuario.dto.UsuarioRequestDTO;
import com.cafeteria.usuario.dto.UsuarioResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioFakerServiceTest {

    @Mock
    private UsuarioService usuarioService; // <-- Corregido: Tu Faker inyecta este servicio directamente

    @InjectMocks
    private UsuarioFakerService usuarioFakerService;

    @Test
    @DisplayName("CP-FAK-001: Debería generar y guardar la cantidad exacta de usuarios falsos invocando al servicio")
    void generarUsuariosFalsosExitoso() {
        // GIVEN
        int cantidadSolicitada = 3;
        UsuarioResponseDTO responseSimulado = new UsuarioResponseDTO(
                1L, "Nombre Falso", "falso@mail.com", "91234", "Direccion Falsa", "CLIENTE", true
        );

        // Simulamos que el método guardar del servicio siempre responde correctamente
        when(usuarioService.guardar(any(UsuarioRequestDTO.class))).thenReturn(responseSimulado);

        // WHEN
        List<UsuarioResponseDTO> resultado = usuarioFakerService.generarUsuariosFalsos(cantidadSolicitada);

        // THEN
        assertNotNull(resultado, "La lista no debería ser nula");
        assertEquals(cantidadSolicitada, resultado.size(), "La lista debe tener exactamente la cantidad pedida");
        assertEquals("Nombre Falso", resultado.get(0).getNombre(), "El DTO mapeado debe contener los datos devueltos por el servicio");

        // Verificamos que el bucle llamó a guardar exactamente la cantidad de veces requerida (3 veces)
        verify(usuarioService, times(cantidadSolicitada)).guardar(any(UsuarioRequestDTO.class));
    }
}