package com.dravz.foro_hub.domain.topico;

import java.time.LocalDateTime;

public record RespuestaTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Long idUsuario,
        Long idCurso
) {
    public RespuestaTopicoDTO(Topico savedTopico) {
        this(
                savedTopico.getId(),
                savedTopico.getTitulo(),
                savedTopico.getMensaje(),
                savedTopico.getFechaCreacion(),
                savedTopico.getUsuario().getId(),
                savedTopico.getCurso().getId()
        );
    }
}
