package com.cafeteria.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO extends RepresentationModel<UsuarioResponseDTO> {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String rol;
    private Boolean activo;
}