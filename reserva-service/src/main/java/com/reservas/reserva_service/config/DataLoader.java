package com.reservas.reserva_service.config;

import net.datafaker.Faker;
import com.reservas.reserva_service.entity.Reserva;
import com.reservas.reserva_service.repository.ReservaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final ReservaRepository reservaRepository;

    @PostConstruct
    public void cargarDatos() {

        if(reservaRepository.count() > 0){
            return;
        }

        Faker faker = new Faker();

        for(int i = 0; i < 10; i++){

            Reserva reserva = new Reserva();

            reserva.setNombreCliente(
                    faker.name().fullName());

            reserva.setCorreoCliente(
                    faker.internet().emailAddress());

            reserva.setCantidadPersonas(
                    faker.number().numberBetween(1,8));

            reservaRepository.save(reserva);
        }
    }
}