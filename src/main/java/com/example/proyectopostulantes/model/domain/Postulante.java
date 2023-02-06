package com.example.proyectopostulantes.model.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Postulante")
@Data
public class Postulante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;

    private String apellidos;

    private String telefono;

    private String dni;

    private String foto;

    private String correo;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInscripcion;

    private String direccion;
    @PrePersist
    public void colocarFecha(){
        this.fechaInscripcion = new Date();
    }

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Colegio colegio;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Estado estado;
}
