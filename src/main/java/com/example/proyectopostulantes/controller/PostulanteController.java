package com.example.proyectopostulantes.controller;

import com.example.proyectopostulantes.model.domain.Colegio;
import com.example.proyectopostulantes.model.domain.Postulante;
import com.example.proyectopostulantes.model.dto.PostulanteDTO;
import com.example.proyectopostulantes.service.ColegioService;
import com.example.proyectopostulantes.service.PostulanteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/postulante")
public class PostulanteController {
    @Value("${app.storage.path}")
    private String STORAGEPATH;
    @Autowired
    ColegioService colegioService;

    @Autowired
    PostulanteService postulanteService;
    Logger LOGGER = LoggerFactory.getLogger(PostulanteController.class);

    @GetMapping("/list")
    public String lista(Model model, @RequestParam(name = "dni", required = false) String dni){
        List<PostulanteDTO> postulanteDTOS = postulanteService.listarPostulantes(dni);
        model.addAttribute("postulantes", postulanteDTOS);
        return "listaPostulantes.html";
    }

    @GetMapping("/form")
    public String crear(Model model){
        PostulanteDTO postulante = new PostulanteDTO();
        model.addAttribute("postulante", postulante);
        Colegio colegio = new Colegio();
        colegio.setId(null);
        colegio.setNombre("Seleccione un colegio");
        List<Colegio> colegios = this.colegioService.findAll();
        colegios.add(0, colegio);
        model.addAttribute("colegios", colegios);
        return "formularioPostulante.html";
    }
    @GetMapping("/form/{id}")
    public String editar(Model model, @PathVariable(value = "id") Long id){
        if(id>=0){
            PostulanteDTO postulante = postulanteService.findPostulanteById(id);
            model.addAttribute("postulante", postulante);
            Colegio colegio = new Colegio();
            colegio.setId(null);
            colegio.setNombre("Seleccione un colegio");
            List<Colegio> colegios = this.colegioService.findAll();
            colegios.add(0, colegio);
            model.addAttribute("colegios", colegios);
            return "formularioPostulante.html";
        }
        else{
            return "redirect:/postulante/list";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable(value = "id") Long id){
        postulanteService.deshabilitarPostulante(id);
        return "redirect:/postulante/list";
    }
    @GetMapping("/imagen/{id}")
    public String verImagen(@PathVariable(value="id") Long id, Model model){
        PostulanteDTO postulante = postulanteService.findPostulanteById(id);
        return null;
    }
    @PostMapping("/form")
    public String inscribir(@Valid @ModelAttribute("postulante") PostulanteDTO postulante, BindingResult result, Model model, @RequestParam("file")MultipartFile foto){
        LOGGER.info(postulante.toString());
        if(result.hasErrors()){
            LOGGER.info("Hay errores" + result.getAllErrors());
            Colegio colegio = new Colegio();
            colegio.setId(null);
            colegio.setNombre("Seleccione un colegio");
            List<Colegio> colegios = this.colegioService.findAll();
            colegios.add(0, colegio);
            model.addAttribute("colegios", colegios);
            return "formularioPostulante.html";
        }
        if(!foto.isEmpty()){
            String rootPath = STORAGEPATH;
            try {
                byte[] bytes = foto.getBytes();
                LOGGER.info(String.valueOf(bytes.length));
                Path rutaCompleta = Paths.get(rootPath + "//" + "[" + UUID.randomUUID().toString() + "]"+foto.getOriginalFilename());
                Files.write(rutaCompleta, bytes);
                postulante.setFoto(foto.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        postulanteService.savePostulante(postulante);
        return "redirect:/postulante/list";
    }

}
