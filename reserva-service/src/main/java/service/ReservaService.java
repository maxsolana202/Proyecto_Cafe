package com.reservas.reserva_service.service;

import com.reservas.reserva_service.entity.Reserva;
import com.reservas.reserva_service.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    // Crear reserva
    public Reserva guardarReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    // Listar reservas
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    // Buscar reserva por ID
    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    // Eliminar reserva
    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }
}