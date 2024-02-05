package com.example.proyectopostulantes.service.implementation;

import com.example.proyectopostulantes.enums.EstadoEnum;
import com.example.proyectopostulantes.model.domain.Colegio;
import com.example.proyectopostulantes.model.domain.Estado;
import com.example.proyectopostulantes.model.domain.Postulante;
import com.example.proyectopostulantes.model.dto.PostulanteDTO;
import com.example.proyectopostulantes.repository.ColegioRepository;
import com.example.proyectopostulantes.repository.EstadoRepository;
import com.example.proyectopostulantes.repository.PostulanteRepository;
import com.example.proyectopostulantes.repository.dao.PostulanteDao;
import com.example.proyectopostulantes.service.PostulanteService;
import com.example.proyectopostulantes.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class IPostulanteService implements PostulanteService {

    @Autowired
    PostulanteRepository postulanteRepository;
    @Autowired
    PostulanteDao postulanteDao;
    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    ColegioRepository colegioRepository;
    @Override
    @Transactional
    public void savePostulante(PostulanteDTO postulanteDTO) {
        Postulante postulante;
        if(postulanteDTO.getId() == null){
            postulante = new Postulante();
            ConvertPostulanteDTOToPostulante(postulanteDTO, postulante);
            Estado estado = estadoRepository.findEstadoByAbreviatura(EstadoEnum.ACTIVO.name());
            postulante.setEstado(estado);
            Colegio colegio = colegioRepository.findById(postulanteDTO.getIdColegio()).orElse(null);
            postulante.setColegio(colegio);
        }
        else{
            postulante = postulanteRepository.findById(postulanteDTO.getId()).orElse(null);
            ConvertPostulanteDTOToPostulante(postulanteDTO, postulante);
            Colegio colegio = colegioRepository.findById(postulanteDTO.getIdColegio()).orElse(null);
            postulante.setColegio(colegio);
        }

        postulanteRepository.save(postulante);
    }

    private void ConvertPostulanteDTOToPostulante(PostulanteDTO postulanteDTO, Postulante postulante) {
        postulante.setNombres(postulanteDTO.getNombres());
        postulante.setApellidos(postulanteDTO.getApellidos());
        postulante.setDni(postulanteDTO.getDni());
        postulante.setFoto(postulanteDTO.getFoto());
        postulante.setDireccion(postulanteDTO.getDireccion());
        postulante.setTelefono(postulanteDTO.getTelefono());
        postulante.setCorreo(postulanteDTO.getCorreo());
        if(postulanteDTO.getFechaInscripcion().isEmpty()){
            postulante.setFechaInscripcion(DateUtil.convertStringToDate(new Date().toString(), DateUtil.FORMAT_DATE_XML));
        }
    }

    @Override
    public List<PostulanteDTO> listarPostulantes() {
        List<PostulanteDTO> postulanteDTOS = postulanteRepository.findAllActivos(EstadoEnum.ACTIVO.name()).stream().map(PostulanteDTO::new).toList();
        return postulanteDTOS;
    }
    @Override
    public List<PostulanteDTO> listarPostulantes(String params) {

        List<PostulanteDTO> postulanteDTOS = postulanteDao.postulanteList(params).stream().map(PostulanteDTO::new).toList();
        return postulanteDTOS;
    }
    @Override
    public PostulanteDTO findPostulanteById(Long id) {
        Postulante postulante = postulanteRepository.findById(id).orElse(null);
        PostulanteDTO postulanteDTO = new PostulanteDTO(postulante);
        return postulanteDTO;
    }

    @Override
    public void deshabilitarPostulante(Long id) {
        Postulante postulante = postulanteRepository.findById(id).orElse(null);
        Estado estado = estadoRepository.findEstadoByAbreviatura(EstadoEnum.DESHABILITADO.name());
        postulante.setEstado(estado);
        postulanteRepository.save(postulante);
    }


}
