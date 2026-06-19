package com.cafe.auth_service;

import com.cafe.auth_service.model.Usuario;
import com.cafe.auth_service.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DataSeeder implements CommandLineRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void run(String... args) throws Exception {
		if (usuarioRepository.count() == 0) {
			Faker faker = new Faker(new Locale("es"));
			System.out.println("Cargando usuarios de prueba...");


			Usuario admin = new Usuario();
			admin.setUsername("admin");
			admin.setPassword("123456");
			admin.setRol("ADMIN");
			usuarioRepository.save(admin);


			for (int i = 0; i < 4; i++) {
				Usuario user = new Usuario();
				user.setUsername(faker.internet().username());
				user.setPassword(faker.internet().password());
				user.setRol(faker.options().option("USER", "MESERO"));
				usuarioRepository.save(user);
			}
			System.out.println("¡Usuarios de prueba cargados!");
		}
	}
}