package com.exemplo.usuario.controller;

import com.exemplo.usuario.dto.*;
import com.exemplo.usuario.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Transações", description = "Gestão de transações financeiras")
@SecurityRequirement(name = "bearerAuth")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping("/transacoes/resumo")
    @Operation(summary = "Retorna resumo financeiro do usuário (salário, receitas, despesas, saldo)")
    public ResumoResponse resumo() {
        return transacaoService.resumo();
    }

    @GetMapping("/transacoes")
    @Operation(summary = "Lista todas as transações do usuário logado")
    public TransacoesListResponse listar() {
        return transacaoService.listar();
    }

    @PostMapping("/transacoes")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Adiciona uma nova transação")
    public TransacaoResponse criar(@Valid @RequestBody TransacaoRequest request) {
        return transacaoService.criar(request);
    }

    @DeleteMapping("/transacoes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove uma transação")
    public void remover(@PathVariable Long id) {
        transacaoService.remover(id);
    }

    @PostMapping("/salario")
    @Operation(summary = "Atualiza o salário do usuário")
    public void atualizarSalario(@Valid @RequestBody SalarioRequest request) {
        transacaoService.atualizarSalario(request);
    }
}
