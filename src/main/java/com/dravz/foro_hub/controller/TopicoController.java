package com.dravz.foro_hub.controller;

import com.dravz.foro_hub.domain.curso.CursoRepository;
import com.dravz.foro_hub.domain.topico.*;
import com.dravz.foro_hub.domain.user.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("topico")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrarTopico(@RequestBody @Valid RegistrarTopicoDTO datos,
                                          UriComponentsBuilder uriComponentsBuilder){

        if(!usuarioRepository.existsById(datos.idUsuario())){
            throw new ValidationException("No se encontro al usuario.");
        }
        if(!cursoRepository.existsById(datos.idCurso())){
            throw new ValidationException("No se encontro el curso");
        }

        if(topicoRepository.existsByTituloOrMensaje(datos.titulo(), datos.mensaje())){
            throw new RuntimeException("Hay un topico con titulo con titulo o mensaje similar.");
        }

        var usuario = usuarioRepository.getReferenceById(datos.idUsuario());
        var curso = cursoRepository.getReferenceById(datos.idCurso());

        var topico = new Topico(
                null,
                datos.titulo(),
                datos.mensaje(),
                datos.fechaCreacion(),
                true,
                usuario,
                curso
        );

        var savedTopico = topicoRepository.save(topico);

        URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(usuario.getId()).toUri();

        var datosRespuestaTopico = new RespuestaTopicoDTO(savedTopico);
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    public ResponseEntity obtenerTopicos(){
        List<DatosRespuestaListaTopicoDTO> listaTopicos = topicoRepository.findByStatusTrue()
                .stream()
                .map(DatosRespuestaListaTopicoDTO::new) // transforma cada Topico a DTO
                .toList(); // Java 16+ (o .collect(Collectors.toList()) si usas Java 8-15)


        return ResponseEntity.ok().body(listaTopicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity obtenerTopicoById(@PathVariable Long id){
        DetallesTopicoDTO respuestaTopicoDTO = new DetallesTopicoDTO(topicoRepository.getReferenceById(id));
        return ResponseEntity.ok().body(respuestaTopicoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaTopicoDTO> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid RegistrarTopicoDTO datos) {

        // Verificar si existe el tópico
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Id del tópico no existe."));

        // Validar usuario
        var usuario = usuarioRepository.findById(datos.idUsuario())
                .orElseThrow(() -> new ValidationException("No se encontró al usuario."));

        // Validar curso
        var curso = cursoRepository.findById(datos.idCurso())
                .orElseThrow(() -> new ValidationException("No se encontró el curso."));

        if (topicoRepository.existsDuplicate(
                datos.titulo(), datos.mensaje(), topico.getId())) {
            throw new RuntimeException("Ya existe un tópico con un título o mensaje similar.");
        }

        // Actualizar los campos del tópico existente
        topico.setTitulo(datos.titulo());
        topico.setMensaje(datos.mensaje());
        topico.setFechaCreacion(datos.fechaCreacion());
        topico.setUsuario(usuario);
        topico.setCurso(curso);

        var actualizado = topicoRepository.save(topico);

        return ResponseEntity.ok(new RespuestaTopicoDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaTopicoDTO> eliminarTopico(
            @PathVariable Long id) {

        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Id del tópico no existe."));


        topico.setStatus(false);

        var actualizado = topicoRepository.save(topico);

        return ResponseEntity.noContent().build();
    }

}
