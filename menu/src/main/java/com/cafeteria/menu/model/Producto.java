package com.cafeteria.menu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 300)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false, length = 50)
    private String categoria;      // BEBIDA, COMIDA, POSTRE, SNACK

    @Column(length = 300)
    private String imagenUrl;      // link de la foto del producto

    @Column(nullable = false)
    private Boolean disponible = true;

    @Column(nullable = false)
    private Integer tiempoPreparacion; // en minutos
}