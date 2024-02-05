package com.example.proyectopostulantes.repository.dao;

import com.example.proyectopostulantes.model.domain.Postulante;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PostulanteDao {
    private final EntityManager entityManager;

    public PostulanteDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Postulante> postulanteList(String params) {
        Query query = entityManager.createQuery("SELECT p FROM Postulante p WHERE p.estado.abreviatura = 'ACTIVO' AND p.dni = '" + params + "'");
        return (List<Postulante>) query.getResultList();
    }
}
