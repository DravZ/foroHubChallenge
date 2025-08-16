package com.dravz.foro_hub.domain.user.perfil;

public record DatosPerfilDTO(
        Long id,
        String nombre
) {
    public DatosPerfilDTO(Perfil perfil) {
        this(
                perfil.getId(),
                perfil.getNombre()
        );
    }
}
