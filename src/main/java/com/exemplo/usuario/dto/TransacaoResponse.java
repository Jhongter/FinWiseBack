package com.exemplo.usuario.dto;

import com.exemplo.usuario.domain.Transacao;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoResponse(
    Long id,
    String description,
    BigDecimal amount,
    String type,
    String category,
    LocalDate date
) {
    public static TransacaoResponse de(Transacao t) {
        return new TransacaoResponse(
            t.getId(),
            t.getDescricao(),
            t.getValor(),
            t.getTipo(),
            t.getCategoria(),
            t.getData()
        );
    }
}
