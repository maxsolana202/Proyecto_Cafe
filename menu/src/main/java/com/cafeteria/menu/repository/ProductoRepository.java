package com.cafeteria.menu.repository;

import com.cafeteria.menu.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoria(String categoria);


    List<Producto> findByDisponibleTrue();
}