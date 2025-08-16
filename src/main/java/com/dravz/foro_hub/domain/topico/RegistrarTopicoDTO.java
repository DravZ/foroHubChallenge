package com.dravz.foro_hub.domain.topico;

import com.dravz.foro_hub.domain.curso.Curso;
import com.dravz.foro_hub.domain.user.usuario.Usuario;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RegistrarTopicoDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        LocalDateTime fechaCreacion,
        @NotNull
        Long idUsuario,
        @NotNull
        Long idCurso
) {
}
