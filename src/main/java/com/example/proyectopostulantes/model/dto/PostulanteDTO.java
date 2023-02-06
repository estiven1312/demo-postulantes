package com.example.proyectopostulantes.model.dto;

import com.example.proyectopostulantes.model.domain.Colegio;
import com.example.proyectopostulantes.model.domain.Estado;
import com.example.proyectopostulantes.model.domain.Postulante;
import com.example.proyectopostulantes.util.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class PostulanteDTO {
    private Long id;
    @NotEmpty
    private String nombres;
    @NotEmpty
    private String apellidos;
    @NotEmpty
    private String telefono;
    @NotEmpty
    private String dni;

    private String foto;
    @NotEmpty
    @Email
    private String correo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String fechaInscripcion;
    @NotEmpty
    private String direccion;
    @NotNull
    private Long idColegio;

    private Long idEstado;

    public PostulanteDTO (Postulante postulante){
        this.id = postulante.getId();
        this.dni = postulante.getDni();
        this.correo = postulante.getCorreo();
        this.nombres = postulante.getNombres();
        this.apellidos = postulante.getApellidos();
        this.direccion = postulante.getDireccion();
        this.telefono = postulante.getTelefono();
        this.fechaInscripcion = DateUtil.convertDateToString(postulante.getFechaInscripcion(), DateUtil.FORMAT_DATE_XML);
        this.idColegio = postulante.getColegio().getId();
        this.foto = postulante.getFoto();
        this.correo = postulante.getCorreo();
        this.idEstado = postulante.getEstado().getId();


    }

}
