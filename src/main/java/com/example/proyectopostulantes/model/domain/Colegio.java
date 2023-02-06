package com.example.proyectopostulantes.model.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "colegio")
@Data
public class Colegio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String codigoModular;
}
