package com.cafeteria.usuario.controller;

import com.cafeteria.usuario.dto.UsuarioRequestDTO;
import com.cafeteria.usuario.dto.UsuarioResponseDTO;
import com.cafeteria.usuario.service.UsuarioFakerService;
import com.cafeteria.usuario.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioFakerService usuarioFakerService; // <-- ¡Clave! Mockeamos el servicio faker inyectado

    @InjectMocks
    private UsuarioController usuarioController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Inicializa MockMvc en modo Standalone aislado
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        objectMapper = new ObjectMapper();
    }

    // ==========================================
    // 1. GET /api/usuarios (OBTENER TODOS)
    // ==========================================
    @Test
    @DisplayName("CP-CON-001: GET /api/usuarios - Debería retornar lista de usuarios con estado 200 OK")
    void obtenerTodosLosUsuarios() throws Exception {
        UsuarioResponseDTO u = new UsuarioResponseDTO(1L, "Carlos", "carlos@gmail.com", "9123", "Dir", "CLIENTE", true);
        when(usuarioService.obtenerTodos()).thenReturn(List.of(u));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Carlos"));
    }

    // ==========================================
    // 2. GET /api/usuarios/{id} (BUSCAR POR ID)
    // ==========================================
    @Test
    @DisplayName("CP-CON-002: GET /api/usuarios/{id} - Debería retornar usuario y enlaces HATEOAS")
    void obtenerPorIdExitoso() throws Exception {
        UsuarioResponseDTO u = new UsuarioResponseDTO(1L, "Carlos", "carlos@gmail.com", "9123", "Dir", "CLIENTE", true);
        when(usuarioService.obtenerPorId(1L)).thenReturn(Optional.of(u));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    @DisplayName("CP-CON-003: GET /api/usuarios/{id} - Debería retornar 404 si el ID no existe")
    void obtenerPorIdNoEncontrado() throws Exception {
        when(usuarioService.obtenerPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/999"))
                .andExpect(status().isNotFound());
    }

    // ==========================================
    // 3. GET /api/usuarios/email/{email}
    // ==========================================
    @Test
    @DisplayName("CP-CON-004: GET /api/usuarios/email/{email} - Debería retornar usuario si el email existe")
    void obtenerPorEmailExitoso() throws Exception {
        UsuarioResponseDTO u = new UsuarioResponseDTO(1L, "Carlos", "carlos@gmail.com", "9123", "Dir", "CLIENTE", true);
        when(usuarioService.obtenerPorEmail("carlos@gmail.com")).thenReturn(Optional.of(u));

        mockMvc.perform(get("/api/usuarios/email/carlos@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("carlos@gmail.com"));
    }

    // ==========================================
    // 4. POST /api/usuarios (CREAR)
    // ==========================================
    @Test
    @DisplayName("CP-CON-005: POST /api/usuarios - Debería retornar 201 Created al guardar")
    void crearUsuarioExitoso() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Carlos", "carlos@gmail.com", "123", "9123", "Dir", "CLIENTE");
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Carlos", "carlos@gmail.com", "9123", "Dir", "CLIENTE", true);

        when(usuarioService.guardar(any(UsuarioRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // <-- Cambiado a 201 Created para que coincida con tu controller
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    // ==========================================
    // 5. PUT /api/usuarios/{id} (ACTUALIZAR)
    // ==========================================
    @Test
    @DisplayName("CP-CON-006: PUT /api/usuarios/{id} - Debería actualizar datos correctamente")
    void actualizarUsuarioExitoso() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO("Carlos Modificado", "carlos@gmail.com", "123", "9123", "Dir", "CLIENTE");
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Carlos Modificado", "carlos@gmail.com", "9123", "Dir", "CLIENTE", true);

        when(usuarioService.actualizar(eq(1L), any(UsuarioRequestDTO.class))).thenReturn(Optional.of(response));

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos Modificado"));
    }

    // ==========================================
    // 6. PATCH /api/usuarios/{id}/desactivar
    // ==========================================
    @Test
    @DisplayName("CP-CON-007: PATCH /api/usuarios/{id}/desactivar - Debería retornar usuario inactivo")
    void desactivarUsuarioExitoso() throws Exception {
        UsuarioResponseDTO response = new UsuarioResponseDTO(1L, "Carlos", "carlos@gmail.com", "9123", "Dir", "CLIENTE", false);
        when(usuarioService.desactivar(1L)).thenReturn(Optional.of(response));

        mockMvc.perform(patch("/api/usuarios/1/desactivar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo").value(false));
    }

    // ==========================================
    // 7. DELETE /api/usuarios/{id} (ELIMINAR)
    // ==========================================
    @Test
    @DisplayName("CP-CON-008: DELETE /api/usuarios/{id} - Debería retornar 244 No Content al borrar")
    void eliminarUsuarioExitoso() throws Exception {
        UsuarioResponseDTO u = new UsuarioResponseDTO(1L, "Carlos", "carlos@gmail.com", "9123", "Dir", "CLIENTE", true);

        // Simulamos que el usuario existe para pasar la validación if de tu controller
        when(usuarioService.obtenerPorId(1L)).thenReturn(Optional.of(u));
        doNothing().when(usuarioService).eliminar(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent()); // 204 No Content

        verify(usuarioService, times(1)).eliminar(1L);
    }

    // ==========================================
    // 8. GET /api/usuarios/auth/usuarios
    // ==========================================
    @Test
    @DisplayName("CP-CON-009: GET /api/usuarios/auth/usuarios - Debería retornar la respuesta del servicio remoto")
    void obtenerUsuariosAuthExitoso() throws Exception {
        when(usuarioService.obtenerUsuariosAuth()).thenReturn(List.of("auth-user1"));

        mockMvc.perform(get("/api/usuarios/auth/usuarios"))
                .andExpect(status().isOk());
    }

    // ==========================================
    // 9. GET /api/usuarios/generar-falsos/{cantidad}
    // ==========================================
    @Test
    @DisplayName("CP-CON-010: GET /api/usuarios/generar-falsos/{cantidad} - Debería llamar al faker service")
    void generarFalsosExitoso() throws Exception {
        UsuarioResponseDTO falso = new UsuarioResponseDTO(99L, "Falso", "falso@mail.com", "000", "Dir", "CLIENTE", true);
        when(usuarioFakerService.generarUsuariosFalsos(3)).thenReturn(List.of(falso));

        mockMvc.perform(get("/api/usuarios/generar-falsos/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}