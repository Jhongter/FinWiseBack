package com.exemplo.usuario.dto;

import jakarta.validation.constraints.*;

public record TrocaSenhaRequest(
    @NotBlank(message = "A senha atual é obrigatória")
    String senhaAtual,

    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 6, message = "A nova senha deve ter no mínimo 6 caracteres")
    String novaSenha
) {}
