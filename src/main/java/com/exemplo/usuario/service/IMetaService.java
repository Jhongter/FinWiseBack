package com.exemplo.usuario.service;

import com.exemplo.usuario.dto.*;
import java.util.List;

public interface IMetaService {
    List<MetaResponse> listar(String email);
    MetaResponse criar(String email, MetaRequest request);
    MetaResponse adicionarValor(String email, Long id, MetaAdicionarValorRequest request);
    MetaResponse atualizarStatus(String email, Long id, MetaStatusRequest request);
    void remover(String email, Long id);
}
