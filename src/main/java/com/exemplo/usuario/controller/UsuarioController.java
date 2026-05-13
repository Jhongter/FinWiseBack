package com.exemplo.usuario.controller;

import com.exemplo.usuario.dto.UsuarioRequest;
import com.exemplo.usuario.dto.UsuarioResponse;
import com.exemplo.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "CRUD de usuários (requer token JWT)")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um novo usuário")
    public UsuarioResponse criar(@Valid @RequestBody UsuarioRequest request) {
        return usuarioService.criar(request);
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuários")
    public List<UsuarioResponse> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário pelo id")
    public UsuarioResponse buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário existente")
    public UsuarioResponse atualizar(@PathVariable Long id,
                                     @Valid @RequestBody UsuarioRequest request) {
        return usuarioService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove um usuário")
    public void remover(@PathVariable Long id) {
        usuarioService.remover(id);
    }
}
