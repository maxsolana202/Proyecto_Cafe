package com.cafeteria.menu;

import com.cafeteria.menu.dto.ProductoRequestDTO;
import com.cafeteria.menu.dto.ProductoResponseDTO;
import com.cafeteria.menu.model.Producto;
import com.cafeteria.menu.repository.ProductoRepository;
import com.cafeteria.menu.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;
    private ProductoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Café Latte");
        producto.setPrecio(2500.0);

        requestDTO = new ProductoRequestDTO();
        requestDTO.setNombre("Café Latte");
        requestDTO.setPrecio(2500.0);
    }

    @Test
    void cuandoGuardarProducto_entoncesRetornaProductoResponseDTO() {
        // Arrange
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        // Act
        ProductoResponseDTO respuesta = productoService.guardar(requestDTO);

        // Assert
        assertNotNull(respuesta);
        assertEquals("Café Latte", respuesta.getNombre());
        assertEquals(2500.0, respuesta.getPrecio());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void cuandoObtenerPorIdExistente_entoncesRetornaProducto() {
        // Arrange
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        // Act
        Optional<ProductoResponseDTO> resultado = productoService.obtenerPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Café Latte", resultado.get().getNombre());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void cuandoObtenerPorIdInexistente_entoncesRetornaOptionalVacio() {
        // Arrange
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<ProductoResponseDTO> resultado = productoService.obtenerPorId(99L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(productoRepository, times(1)).findById(99L);
    }
}