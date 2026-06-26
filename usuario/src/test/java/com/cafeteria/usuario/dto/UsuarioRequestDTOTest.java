package com.cafeteria.usuario.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRequestDTOTest {

    private final Validator validator;

    public UsuarioRequestDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deberiaFallarSiElNombreEstaVacio() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO(
                "", "juan@gmail.com", "1234", "+56912345678", "Calle 1", "CLIENTE");

        Set<ConstraintViolation<UsuarioRequestDTO>> violaciones = validator.validate(dto);

        assertFalse(violaciones.isEmpty());
    }

    @Test
    void deberiaPasarConDatosCorrectos() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO(
                "Juan Pérez", "juan@gmail.com", "1234", "+56912345678", "Calle 1", "CLIENTE");

        Set<ConstraintViolation<UsuarioRequestDTO>> violaciones = validator.validate(dto);

        assertTrue(violaciones.isEmpty());
    }
}