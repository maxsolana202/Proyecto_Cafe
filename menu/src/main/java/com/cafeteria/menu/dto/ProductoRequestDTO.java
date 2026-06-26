package com.cafeteria.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio no puede estar vacío")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotBlank(message = "La categoría no puede estar vacía")
    private String categoria;

    private String imagenUrl;

    @NotNull(message = "El tiempo de preparación no puede estar vacío")
    @Positive(message = "El tiempo de preparación debe ser mayor a 0")
    private Integer tiempoPreparacion;
}