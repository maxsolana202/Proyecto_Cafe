package com.cafeteria.menu.service;

import com.cafeteria.menu.dto.ProductoRequestDTO;
import com.cafeteria.menu.dto.ProductoResponseDTO;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductoFakerService {

    private final ProductoService productoService;
    private final Faker faker = new Faker();

    public List<ProductoResponseDTO> generarProductosFalsos(int cantidad) {
        return IntStream.range(0, cantidad)
                .mapToObj(i -> {

                    // Creamos el Record pasando los datos en el orden estándar de tu modelo:
                    // 1. Nombre, 2. Descripción, 3. Precio (Double), 4. Categoría, 5. ImagenUrl, 6. Disponible (Boolean), 7. TiempoPreparacion (Integer)
                    ProductoRequestDTO dto = new ProductoRequestDTO(
                            faker.food().dish(),                                      // nombre
                            "Delicioso plato preparado con ingredientes frescos.",     // descripcion
                            faker.number().randomDouble(2, 1500, 7500),               // precio (Double)
                            "COMIDA",                                                 // categoria (COMIDA, BEBIDA, etc.)
                            "https://images.unsplash.com/photo-1546069901-ba9599a7e63c", // imagenUrl
                            // disponible (Boolean)
                            faker.number().numberBetween(5, 20)                       // tiempoPreparacion (Integer)
                    );

                    return productoService.guardar(dto);
                })
                .collect(Collectors.toList());
    }
}