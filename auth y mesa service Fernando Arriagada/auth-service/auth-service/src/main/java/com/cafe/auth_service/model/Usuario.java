package com.cafe.auth_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "usuarios")
@Data
@EqualsAndHashCode(callSuper = false)
public class Usuario extends RepresentationModel<Usuario> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    @Column(nullable = false)
    private String rol;
}