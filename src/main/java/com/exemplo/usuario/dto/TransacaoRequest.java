package com.exemplo.usuario.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoRequest(
    @NotBlank(message = "A descrição é obrigatória")
    String description,

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    BigDecimal amount,

    @NotBlank(message = "O tipo é obrigatório")
    @Pattern(regexp = "income|expense", message = "Tipo deve ser 'income' ou 'expense'")
    String type,

    String category,

    @NotNull(message = "A data é obrigatória")
    LocalDate date
) {}
