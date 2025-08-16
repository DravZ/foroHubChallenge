package com.dravz.foro_hub.controller;

import com.dravz.foro_hub.domain.user.perfil.PerfilRepository;
import com.dravz.foro_hub.domain.user.usuario.*;
import com.dravz.foro_hub.infra.security.DatosJWTToken;
import com.dravz.foro_hub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("auth")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/register")
    public ResponseEntity registrarUsuario(@RequestBody @Valid RegistrarUsuarioDTO datos,
                                           UriComponentsBuilder uriComponentsBuilder){
        var contrasena = passwordEncoder.encode(datos.contrasena());

        if(!perfilRepository.existsById(datos.idPerfil())){
            throw new RuntimeException("No se encontro el perfil");
        }

        var usuario = new Usuario(null, datos.nombre(), datos.correoElectronico(), contrasena,
                perfilRepository.getReferenceById(datos.idPerfil()));

        usuarioRepository.save(usuario);

        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(usuario.getId()).toUri();

        var datosRespuestaUsuario = new RespuestaUsuarioDTO(usuario);
        return ResponseEntity.created(url).body(datosRespuestaUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid LoginUsuarioDTO datosAutenticacionUsuario) {

        System.out.println(datosAutenticacionUsuario);
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.correoElectronico(),
                datosAutenticacionUsuario.contrasena());

        System.out.println("Intento login");
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        System.out.println("Intento login paso 2");
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        System.out.println("Intento login paso 3");
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}
