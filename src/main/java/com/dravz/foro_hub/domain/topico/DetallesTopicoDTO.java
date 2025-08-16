package com.dravz.foro_hub.domain.topico;

import java.time.LocalDateTime;

public record DetallesTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String usuario,
        String curso
) {
    public DetallesTopicoDTO(Topico savedTopico) {
        this(
                savedTopico.getId(),
                savedTopico.getTitulo(),
                savedTopico.getMensaje(),
                savedTopico.getFechaCreacion(),
                savedTopico.getUsuario().getNombre(),
                savedTopico.getCurso().getNombre()
        );
    }
}
