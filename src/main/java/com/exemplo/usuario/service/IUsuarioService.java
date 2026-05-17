package com.exemplo.usuario.service;

import com.exemplo.usuario.dto.*;
import java.math.BigDecimal;
import java.util.List;

public interface IUsuarioService {
    UsuarioResponse criar(UsuarioRequest request);
    List<UsuarioResponse> listarTodos();
    UsuarioResponse buscarPorId(Long id);
    UsuarioResponse atualizar(Long id, UsuarioRequest request);
    void remover(Long id);
    void trocarSenha(String email, TrocaSenhaRequest request);
    void atualizarSalario(String email, SalarioRequest request);
}
