package com.dravz.foro_hub.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaListaTopicoDTO(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String Usuario,
        String Curso
) {
    public DatosRespuestaListaTopicoDTO(Topico savedTopico) {
        this(
                savedTopico.getTitulo(),
                savedTopico.getMensaje(),
                savedTopico.getFechaCreacion(),
                savedTopico.getUsuario().getNombre(),
                savedTopico.getCurso().getNombre()
        );
    }
}
