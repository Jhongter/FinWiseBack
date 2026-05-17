package com.exemplo.usuario.service;

import com.exemplo.usuario.dto.*;

public interface ITransacaoService {
    TransacoesListResponse listar(String email);
    TransacaoResponse criar(String email, TransacaoRequest request);
    void remover(String email, Long id);
    ResumoResponse resumo(String email);
}
