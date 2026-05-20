package com.cafe.mesas_service.service;

import com.cafe.mesas_service.model.Mesa;
import com.cafe.mesas_service.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MesaService {
    @Autowired
    private MesaRepository mesaRepository;

    public Mesa guardar(Mesa mesa) {
        return mesaRepository.save(mesa);
    }

    public List<Mesa> listarTodas() {
        return mesaRepository.findAll();
    }
}