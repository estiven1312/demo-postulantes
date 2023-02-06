package com.example.proyectopostulantes.repository;

import com.example.proyectopostulantes.model.domain.Postulante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulanteRepository extends JpaRepository<Postulante, Long> {
    @Query("SELECT postulante FROM Postulante postulante WHERE postulante.estado.abreviatura LIKE :estado")
    public List<Postulante> findAllActivos(@Param("estado") String estado);
    @Query("SELECT postulante FROM Postulante postulante WHERE postulante.nombres LIKE %:nombres% OR postulante.apellidos LIKE %:apellidos%")
    public List<Postulante> findAllByFilters(@Param("nombres") String nombres, @Param("apellidos") String apellidos);

}
