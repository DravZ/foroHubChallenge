package com.dravz.foro_hub.domain.user.perfil;

import jakarta.validation.constraints.NotNull;

public record RegistrarPerfilDTO(
        @NotNull
        String nombre
) {
}
