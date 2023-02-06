package com.example.proyectopostulantes.repository;

import com.example.proyectopostulantes.model.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
    @Query("SELECT estado FROM Estado estado WHERE estado.abreviatura LIKE :abreviatura")
    public Estado findEstadoByAbreviatura(@Param("abreviatura") String abreviatura);
}
