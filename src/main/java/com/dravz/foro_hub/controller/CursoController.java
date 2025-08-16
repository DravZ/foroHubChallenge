package com.dravz.foro_hub.controller;

import com.dravz.foro_hub.domain.curso.Curso;
import com.dravz.foro_hub.domain.curso.CursoRepository;
import com.dravz.foro_hub.domain.curso.RegistrarCursoDTO;
import com.dravz.foro_hub.domain.curso.RespuestaCursoDTO;
import com.dravz.foro_hub.domain.user.usuario.RegistrarUsuarioDTO;
import com.dravz.foro_hub.domain.user.usuario.RespuestaUsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("curso")
public class CursoController {

    @Autowired
    CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity registrarCurso(@RequestBody @Valid RegistrarCursoDTO datos,
                                           UriComponentsBuilder uriComponentsBuilder){

        var curso = new Curso(datos);
        var cursoSaved = cursoRepository.save(curso);

        URI url = uriComponentsBuilder.path("/curso/{id}").buildAndExpand(cursoSaved.getId()).toUri();

        var datosRespuestaCurso = new RespuestaCursoDTO(cursoSaved);
        return ResponseEntity.created(url).body(datosRespuestaCurso);
    }

    @GetMapping
    public ResponseEntity getCursos(){

        List<RespuestaCursoDTO> listaCursos = cursoRepository.findAll().stream()
                .map(RespuestaCursoDTO::new)  // transforma cada curso en DTO
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(listaCursos);
    }

}
