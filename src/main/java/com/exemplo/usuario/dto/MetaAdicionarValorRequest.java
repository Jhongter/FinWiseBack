package com.exemplo.usuario.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/** Corpo de PATCH /metas/{id}/valor */
public record MetaAdicionarValorRequest(

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    BigDecimal amount
) {}
