package com.dravz.foro_hub.domain.user.usuario;

import com.dravz.foro_hub.domain.user.perfil.Perfil;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarUsuarioDTO(
        @NotBlank
        String nombre,
        @Email
        String correoElectronico,
        @NotBlank
        String contrasena,
        @NotNull
        Long idPerfil
) {
}
