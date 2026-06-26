package com.cafe.auth_service;

import com.cafe.auth_service.model.Usuario;
import com.cafe.auth_service.repository.UsuarioRepository;
import com.cafe.auth_service.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceApplicationTests {

	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private UsuarioService usuarioService;

	@Test
	void deberiaHacerLoginCorrecto() {

		Usuario mockUser = new Usuario();
		mockUser.setUsername("testuser");
		mockUser.setPassword("password123");

		when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));


		String token = usuarioService.login("testuser", "password123");


		assertNotNull(token);
		assertTrue(token.startsWith("Bearer "));
	}

	@Test
	void deberiaFallarLoginConClaveIncorrecta() {

		Usuario mockUser = new Usuario();
		mockUser.setUsername("testuser");
		mockUser.setPassword("password123");

		when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));


		String token = usuarioService.login("testuser", "claveEquivocada");


		assertNull(token);
	}
}