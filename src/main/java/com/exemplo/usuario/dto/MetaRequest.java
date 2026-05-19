package com.exemplo.usuario.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record MetaRequest(

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    String title,

    @Size(max = 300, message = "A descrição deve ter no máximo 300 caracteres")
    String description,

    @NotNull(message = "O valor alvo é obrigatório")
    @Positive(message = "O valor alvo deve ser positivo")
    BigDecimal targetAmount,

    @PositiveOrZero(message = "O valor atual não pode ser negativo")
    BigDecimal currentAmount,

    LocalDate deadline,

    @Size(max = 10)
    String emoji,

    @Size(max = 20)
    String color
) {}
