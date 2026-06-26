package com.cafeteria.menu.config;

import com.cafeteria.menu.model.Producto;
import com.cafeteria.menu.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo insertamos datos si la tabla está vacía
        if (productoRepository.count() == 0) {
            Faker faker = new Faker();
            String[] categorias = {"BEBIDA", "COMIDA", "POSTRE", "SNACK"};

            for (int i = 0; i < 10; i++) {
                Producto producto = new Producto();
                producto.setNombre(faker.food().dish());
                producto.setDescripcion(faker.food().ingredient() + " artesanal");
                producto.setPrecio(faker.number().randomDouble(2, 5, 20));
                producto.setCategoria(categorias[faker.number().numberBetween(0, 4)]);
                producto.setImagenUrl("https://picsum.photos/200");
                producto.setDisponible(true);
                producto.setTiempoPreparacion(faker.number().numberBetween(5, 30));

                productoRepository.save(producto);
            }
            System.out.println(">> ¡DataFaker ha poblado la base de datos con 10 productos!");
        }
    }
}