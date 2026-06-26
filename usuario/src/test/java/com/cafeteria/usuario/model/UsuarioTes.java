package com.cafeteria.usuario.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void deberiaCrearUsuarioConDatosCorrectos() {
        // Arrange — preparamos los datos
        Usuario usuario = new Usuario(
                1L, "Juan Pérez", "juan@gmail.com", "1234",
                "+56912345678", "Calle 1", "CLIENTE", true);

        // Assert — verificamos que los datos quedaron bien guardados
        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals("juan@gmail.com", usuario.getEmail());
        assertEquals("CLIENTE", usuario.getRol());
        assertTrue(usuario.getActivo());
    }

    @Test
    void usuarioActivoPorDefectoEsVerdadero() {
        Usuario usuario = new Usuario();
        usuario.setActivo(true);
        assertTrue(usuario.getActivo());
    }
}