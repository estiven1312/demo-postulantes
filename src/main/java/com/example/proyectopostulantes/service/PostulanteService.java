package com.example.proyectopostulantes.service;

import com.example.proyectopostulantes.model.dto.PostulanteDTO;

import java.util.List;

public interface PostulanteService {
    public void savePostulante(PostulanteDTO postulanteDTO);

    public List<PostulanteDTO> listarPostulantes();

    List<PostulanteDTO> listarPostulantes(String params);

    public PostulanteDTO findPostulanteById(Long id);

    public void deshabilitarPostulante(Long id);
}
