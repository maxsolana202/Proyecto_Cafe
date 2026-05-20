package com.cafe.mesas_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "mesas")
@Data
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El número de mesa es obligatorio")
    private Integer numeroMesa;

    @Min(value = 1, message = "La capacidad mínima es de 1 persona")
    private Integer capacidad;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}