package com.cafe.mesas_service.service;

import com.cafe.mesas_service.model.Mesa;
import com.cafe.mesas_service.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    public Mesa guardar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    public List<Mesa> listar() {
        return mesaRepository.findAll();
    }

    public Optional<Mesa> obtenerPorId(Long id) {
        return mesaRepository.findById(id);
    }

    public void eliminar(Long id) {
        mesaRepository.deleteById(id);
    }
}
