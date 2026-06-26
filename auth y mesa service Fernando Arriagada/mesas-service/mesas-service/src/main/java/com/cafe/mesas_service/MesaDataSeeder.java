package com.cafe.mesas_service;

import com.cafe.mesas_service.model.Mesa;
import com.cafe.mesas_service.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MesaDataSeeder implements CommandLineRunner {

    @Autowired
    private MesaRepository mesaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (mesaRepository.count() == 0) {
            System.out.println("Cargando mesas de prueba...");
            int[][] datos = {{1, 2}, {2, 2}, {3, 4}, {4, 4}, {5, 6}, {6, 8}};
            for (int[] d : datos) {
                Mesa mesa = new Mesa();
                mesa.setNumero(d[0]);
                mesa.setCapacidad(d[1]);
                mesa.setEstado("LIBRE");
                mesaRepository.save(mesa);
            }
            System.out.println("¡Mesas de prueba cargadas!");
        }
    }
}
