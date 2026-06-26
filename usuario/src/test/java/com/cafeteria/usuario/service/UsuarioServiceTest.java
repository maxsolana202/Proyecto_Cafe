package com.cafeteria.usuario.service;

import com.cafeteria.usuario.client.AuthClient;
import com.cafeteria.usuario.dto.UsuarioRequestDTO;
import com.cafeteria.usuario.dto.UsuarioResponseDTO;
import com.cafeteria.usuario.model.Usuario;
import com.cafeteria.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository; // Clon falso de la base de datos

    @Mock
    private AuthClient authClient; // <-- ¡CLAVE! Mockeamos el cliente de autenticación externo

    @InjectMocks
    private UsuarioService usuarioService; // El servicio real bajo prueba

    // ==========================================
    // TESTS: GUARDAR USUARIO
    // ==========================================

    @Test
    @DisplayName("CP-001: Debería guardar un usuario exitosamente cuando el email no está registrado")
    void guardarUsuarioExitoso() {
        UsuarioRequestDTO request = new UsuarioRequestDTO(
                "Carlos Zuñiga", "carlos@gmail.com", "password123", "+56912345678", "Av. Concha y Toro 1230", "CLIENTE"
        );

        Usuario usuarioSimulado = new Usuario(
                1L, request.getNombre(), request.getEmail(), request.getPassword(),
                request.getTelefono(), request.getDireccion(), request.getRol(), true
        );

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSimulado);

        UsuarioResponseDTO resultado = usuarioService.guardar(request);

        assertNotNull(resultado);
        assertEquals("Carlos Zuñiga", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("CP-002: Debería lanzar una excepción cuando el email ya existe")
    void guardarUsuarioErrorEmailDuplicado() {
        UsuarioRequestDTO request = new UsuarioRequestDTO(
                "Carlos Zuñiga", "carlos@gmail.com", "password123", "+56912345678", "Av. Concha y Toro 1230", "CLIENTE"
        );

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(true);

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            usuarioService.guardar(request);
        });

        assertEquals("Ya existe un usuario con ese email", excepcion.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    // ==========================================
    // TESTS: OBTENER USUARIOS
    // ==========================================

    @Test
    @DisplayName("CP-003: Debería listar todos los usuarios registrados")
    void obtenerTodosLosUsuarios() {
        Usuario usuario = new Usuario(1L, "Carlos", "carlos@gmail.com", "123", "9123", "Dir", "CLIENTE", true);
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioResponseDTO> resultado = usuarioService.obtenerTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("CP-004: Debería retornar un Optional con el usuario si el ID existe")
    void obtenerPorIdExistente() {
        Usuario usuario = new Usuario(1L, "Carlos", "carlos@gmail.com", "123", "9123", "Dir", "CLIENTE", true);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<UsuarioResponseDTO> resultado = usuarioService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getNombre());
    }

    @Test
    @DisplayName("CP-005: Debería retornar un Optional vacío si el ID no existe")
    void obtenerPorIdNoExistente() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<UsuarioResponseDTO> resultado = usuarioService.obtenerPorId(999L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("CP-006: Debería retornar un Optional con el usuario si el Email existe")
    void obtenerPorEmailExistente() {
        Usuario usuario = new Usuario(1L, "Carlos", "carlos@gmail.com", "123", "9123", "Dir", "CLIENTE", true);
        when(usuarioRepository.findByEmail("carlos@gmail.com")).thenReturn(Optional.of(usuario));

        Optional<UsuarioResponseDTO> resultado = usuarioService.obtenerPorEmail("carlos@gmail.com");

        assertTrue(resultado.isPresent());
        assertEquals("carlos@gmail.com", resultado.get().getEmail());
    }

    // ==========================================
    // TESTS: ACTUALIZAR Y ACCIONES
    // ==========================================

    @Test
    @DisplayName("CP-007: Debería actualizar los datos de un usuario existente")
    void actualizarUsuarioExistente() {
        Usuario usuarioExistente = new Usuario(1L, "Carlos", "carlos@gmail.com", "123", "9123", "Dir", "CLIENTE", true);
        UsuarioRequestDTO dtoActualizacion = new UsuarioRequestDTO(
                "Carlos Editado", "carlos@gmail.com", "newpass", "+569999", "Nueva Dir", "ADMIN"
        );
        Usuario usuarioActualizado = new Usuario(1L, "Carlos Editado", "carlos@gmail.com", "newpass", "+569999", "Nueva Dir", "ADMIN", true);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        Optional<UsuarioResponseDTO> resultado = usuarioService.actualizar(1L, dtoActualizacion);

        assertTrue(resultado.isPresent());
        assertEquals("Carlos Editado", resultado.get().getNombre());
        assertEquals("ADMIN", resultado.get().getRol());
    }

    @Test
    @DisplayName("CP-008: Debería desactivar un usuario pasando su estado 'activo' a false")
    void desactivarUsuarioExitoso() {
        Usuario usuarioActivo = new Usuario(1L, "Carlos", "carlos@gmail.com", "123", "9123", "Dir", "CLIENTE", true);
        Usuario usuarioInactivo = new Usuario(1L, "Carlos", "carlos@gmail.com", "123", "9123", "Dir", "CLIENTE", false);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioActivo));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioInactivo);

        Optional<UsuarioResponseDTO> resultado = usuarioService.desactivar(1L);

        assertTrue(resultado.isPresent());
        assertFalse(resultado.get().getActivo(), "El usuario debería quedar inactivo (false)");
    }

    @Test
    @DisplayName("CP-009: Debería invocar la eliminación por ID en el repositorio")
    void eliminarUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.eliminar(1L);

        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    // ==========================================
    // TEST: CLIENTE FEIGN (AUTH)
    // ==========================================

    @Test
    @DisplayName("CP-010: Debería retornar la lista de usuarios desde el AuthClient remoto")
    void obtenerUsuariosAuthExitoso() {
        List<String> usuariosRemotosFalsos = List.of("user1", "user2");
        when(authClient.listarUsuarios()).thenReturn(usuariosRemotosFalsos);

        Object resultado = usuarioService.obtenerUsuariosAuth();

        assertNotNull(resultado);
        verify(authClient, times(1)).listarUsuarios();
    }
}