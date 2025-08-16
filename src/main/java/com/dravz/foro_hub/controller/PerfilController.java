package com.dravz.foro_hub.controller;

import com.dravz.foro_hub.domain.user.perfil.DatosPerfilDTO;
import com.dravz.foro_hub.domain.user.perfil.Perfil;
import com.dravz.foro_hub.domain.user.perfil.PerfilRepository;
import com.dravz.foro_hub.domain.user.perfil.RegistrarPerfilDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("perfil")
public class PerfilController {
    @Autowired
    private PerfilRepository repository;
    @PostMapping("/")
    public ResponseEntity crearPerfil(@RequestBody @Valid RegistrarPerfilDTO datos){

        var perfil = repository.save(new Perfil(datos));

        return ResponseEntity.ok().body(new DatosPerfilDTO(perfil));
    }
}
