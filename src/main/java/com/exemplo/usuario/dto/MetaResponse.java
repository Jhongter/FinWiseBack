package com.exemplo.usuario.dto;

import com.exemplo.usuario.domain.Meta;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Campos em inglês para manter compatibilidade com o frontend JS
 * (que usa title, targetAmount, currentAmount, etc.)
 */
public record MetaResponse(
    String id,           // String para compatibilidade com frontend que usa String IDs
    String title,
    String description,
    BigDecimal targetAmount,
    BigDecimal currentAmount,
    String status,
    LocalDate deadline,
    String emoji,
    String color
) {
    public static MetaResponse de(Meta m) {
        return new MetaResponse(
            String.valueOf(m.getId()),
            m.getTitulo(),
            m.getDescricao(),
            m.getValorAlvo(),
            m.getValorAtual(),
            m.getStatus(),
            m.getPrazo(),
            m.getEmoji(),
            m.getCor()
        );
    }
}
