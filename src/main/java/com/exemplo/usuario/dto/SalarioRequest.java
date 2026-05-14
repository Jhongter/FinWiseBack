package com.exemplo.usuario.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record SalarioRequest(
    @NotNull(message = "O valor é obrigatório")
    @PositiveOrZero(message = "O salário não pode ser negativo")
    BigDecimal amount
) {}
