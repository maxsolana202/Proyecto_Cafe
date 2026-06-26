package com.cafeteria.usuario.service;

import com.cafeteria.usuario.dto.UsuarioRequestDTO;
import com.cafeteria.usuario.dto.UsuarioResponseDTO;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class UsuarioFakerService {

    private final UsuarioService usuarioService;
    private final Faker faker = new Faker();

    // Genera "cantidad" usuarios falsos y los guarda en la base de datos
    public List<UsuarioResponseDTO> generarUsuariosFalsos(int cantidad) {
        return IntStream.range(0, cantidad)
                .mapToObj(i -> {
                    UsuarioRequestDTO dto = new UsuarioRequestDTO(
                            faker.name().fullName(),
                            faker.internet().emailAddress(),
                            "1234",
                            faker.phoneNumber().phoneNumber(),
                            faker.address().fullAddress(),
                            "CLIENTE"
                    );
                    return usuarioService.guardar(dto);
                })
                .collect(Collectors.toList());
    }
}