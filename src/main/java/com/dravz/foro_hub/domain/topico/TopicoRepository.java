package com.dravz.foro_hub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloOrMensaje(@NotBlank String titulo, @NotBlank String mensaje);
    List<Topico> findByStatusTrue();
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
            "FROM Topico t " +
            "WHERE (t.titulo = :titulo OR t.mensaje = :mensaje) AND t.id <> :id")
    boolean existsDuplicate(@Param("titulo") String titulo,
                            @Param("mensaje") String mensaje,
                            @Param("id") Long id);
}
