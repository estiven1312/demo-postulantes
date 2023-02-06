package com.example.proyectopostulantes.service.implementation;

import com.example.proyectopostulantes.model.domain.Colegio;
import com.example.proyectopostulantes.repository.ColegioRepository;
import com.example.proyectopostulantes.service.ColegioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IColegioService implements ColegioService {

    @Autowired
    ColegioRepository colegioRepository;
    @Override
    public List<Colegio> findAll() {
        return colegioRepository.findAll();
    }
}
