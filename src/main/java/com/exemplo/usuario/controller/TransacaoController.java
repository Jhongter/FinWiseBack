package com.exemplo.usuario.controller;

import com.exemplo.usuario.dto.*;
import com.exemplo.usuario.service.ITransacaoService;
import com.exemplo.usuario.service.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Transações", description = "Gestão de transações financeiras")
@SecurityRequirement(name = "bearerAuth")
public class TransacaoController {

    private final ITransacaoService transacaoService;
    private final IUsuarioService usuarioService;

    public TransacaoController(ITransacaoService transacaoService,
                                IUsuarioService usuarioService) {
        this.transacaoService = transacaoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/transacoes/resumo")
    @Operation(summary = "Retorna resumo financeiro do usuário")
    public ResumoResponse resumo(@AuthenticationPrincipal String email) {
        return transacaoService.resumo(email);
    }

    @GetMapping("/transacoes")
    @Operation(summary = "Lista todas as transações do usuário logado")
    public TransacoesListResponse listar(@AuthenticationPrincipal String email) {
        return transacaoService.listar(email);
    }

    @PostMapping("/transacoes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adiciona uma nova transação")
    public TransacaoResponse criar(@AuthenticationPrincipal String email,
                                   @Valid @RequestBody TransacaoRequest request) {
        return transacaoService.criar(email, request);
    }

    @DeleteMapping("/transacoes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove uma transação")
    public void remover(@AuthenticationPrincipal String email,
                        @PathVariable Long id) {
        transacaoService.remover(email, id);
    }

    @PostMapping("/salario")
    @Operation(summary = "Atualiza o salário do usuário")
    public void atualizarSalario(@AuthenticationPrincipal String email,
                                  @Valid @RequestBody SalarioRequest request) {
        usuarioService.atualizarSalario(email, request);
    }
}
