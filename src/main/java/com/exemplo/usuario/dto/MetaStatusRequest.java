package com.exemplo.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/** Corpo de PATCH /metas/{id}/status */
public record MetaStatusRequest(

    @NotBlank(message = "O status é obrigatório")
    @Pattern(
        regexp = "em_andamento|concluida|pausada",
        message = "Status deve ser 'em_andamento', 'concluida' ou 'pausada'"
    )
    String status
) {}
