package com.dravz.foro_hub.domain.curso;


public record RespuestaCursoDTO(
        Long id,
        String nombre,
        Categoria categoria
) {
    public RespuestaCursoDTO(Curso datos) {
        this(
                datos.getId(),
                datos.getNombre(),
                datos.getCategoria()
        );
    }
}
