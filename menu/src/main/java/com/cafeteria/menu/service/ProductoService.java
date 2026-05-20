package com.cafeteria.menu.service;

import com.cafeteria.menu.dto.ProductoRequestDTO;
import com.cafeteria.menu.dto.ProductoResponseDTO;
import com.cafeteria.menu.model.Producto;
import com.cafeteria.menu.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    private ProductoResponseDTO mapToDTO(Producto p) {
        return new ProductoResponseDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getCategoria(),
                p.getImagenUrl(),
                p.getDisponible(),
                p.getTiempoPreparacion()
        );
    }

    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> obtenerDisponibles() {
        return productoRepository.findByDisponibleTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> obtenerPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductoResponseDTO> obtenerPorId(Long id) {
        return productoRepository.findById(id).map(this::mapToDTO);
    }

    public ProductoResponseDTO guardar(ProductoRequestDTO dto) {
        Producto producto = new Producto(
                null,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getPrecio(),
                dto.getCategoria(),
                dto.getImagenUrl(),
                true,
                dto.getTiempoPreparacion()
        );
        return mapToDTO(productoRepository.save(producto));
    }

    public Optional<ProductoResponseDTO> actualizar(Long id, ProductoRequestDTO dto) {
        return productoRepository.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setDescripcion(dto.getDescripcion());
            existente.setPrecio(dto.getPrecio());
            existente.setCategoria(dto.getCategoria());
            existente.setImagenUrl(dto.getImagenUrl());
            existente.setTiempoPreparacion(dto.getTiempoPreparacion());
            return mapToDTO(productoRepository.save(existente));
        });
    }

    public Optional<ProductoResponseDTO> desactivar(Long id) {
        return productoRepository.findById(id).map(existente -> {
            existente.setDisponible(false);
            return mapToDTO(productoRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}