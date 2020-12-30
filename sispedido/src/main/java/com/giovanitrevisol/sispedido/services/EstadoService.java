package com.giovanitrevisol.sispedido.services;

import com.giovanitrevisol.sispedido.domain.Estado;
import com.giovanitrevisol.sispedido.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    EstadoRepository repo;

    public List<Estado> findAll() {
        return repo.findAllByOrderByNome();
    }
}

