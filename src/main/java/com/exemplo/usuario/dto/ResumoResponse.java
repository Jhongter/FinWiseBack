package com.exemplo.usuario.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ResumoResponse(SummaryData summary) {

    public record SummaryData(
        BigDecimal salary,
        BigDecimal income,
        BigDecimal expenses,
        BigDecimal balance,
        Map<String, BigDecimal> categories
    ) {}
}
