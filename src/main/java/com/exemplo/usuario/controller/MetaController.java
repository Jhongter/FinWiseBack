package com.exemplo.usuario.controller;

import com.exemplo.usuario.dto.*;
import com.exemplo.usuario.service.IMetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metas")
@Tag(name = "Metas", description = "Gestão de metas financeiras")
@SecurityRequirement(name = "bearerAuth")
public class MetaController {

    private final IMetaService metaService;

    public MetaController(IMetaService metaService) {
        this.metaService = metaService;
    }

    @GetMapping
    @Operation(summary = "Lista todas as metas do usuário logado")
    public List<MetaResponse> listar(@AuthenticationPrincipal String email) {
        return metaService.listar(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria uma nova meta")
    public MetaResponse criar(@AuthenticationPrincipal String email,
                               @Valid @RequestBody MetaRequest request) {
        return metaService.criar(email, request);
    }

    @PatchMapping("/{id}/valor")
    @Operation(summary = "Adiciona valor à meta (progresso)")
    public MetaResponse adicionarValor(@AuthenticationPrincipal String email,
                                        @PathVariable Long id,
                                        @Valid @RequestBody MetaAdicionarValorRequest request) {
        return metaService.adicionarValor(email, id, request);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualiza o status da meta (pausar/retomar/concluir)")
    public MetaResponse atualizarStatus(@AuthenticationPrincipal String email,
                                         @PathVariable Long id,
                                         @Valid @RequestBody MetaStatusRequest request) {
        return metaService.atualizarStatus(email, id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma meta")
    public void remover(@AuthenticationPrincipal String email,
                         @PathVariable Long id) {
        metaService.remover(email, id);
    }
}
