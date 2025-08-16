package com.dravz.foro_hub.domain.user.usuario;

public record RespuestaUsuarioDTO(
        Long id,
        String nombre,
        String correoElectronico,
        String perfil
) {
    public RespuestaUsuarioDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                usuario.getPerfil().getNombre()

        );
    }
}
