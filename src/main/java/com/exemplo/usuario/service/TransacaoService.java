package com.exemplo.usuario.service;

import com.exemplo.usuario.domain.Transacao;
import com.exemplo.usuario.domain.Usuario;
import com.exemplo.usuario.dto.*;
import com.exemplo.usuario.repository.TransacaoRepository;
import com.exemplo.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransacaoService implements ITransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public TransacaoService(TransacaoRepository transacaoRepository,
                            UsuarioRepository usuarioRepository) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario buscarUsuario(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    @Override
    public TransacoesListResponse listar(String email) {
        List<TransacaoResponse> lista = transacaoRepository
                .findByUsuarioEmailOrderByDataDesc(email)
                .stream()
                .map(TransacaoResponse::de)
                .collect(Collectors.toList());
        return new TransacoesListResponse(lista);
    }

    @Override
    @Transactional
    public TransacaoResponse criar(String email, TransacaoRequest req) {
        Usuario usuario = buscarUsuario(email);
        Transacao t = new Transacao(
                req.description(), req.amount(), req.type(),
                req.category(), req.date(), usuario
        );
        return TransacaoResponse.de(transacaoRepository.save(t));
    }

    @Override
    @Transactional
    public void remover(String email, Long id) {
        Transacao t = transacaoRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada"));
        transacaoRepository.delete(t);
    }

    @Override
    public ResumoResponse resumo(String email) {
        Usuario usuario = buscarUsuario(email);
        List<Transacao> transacoes = transacaoRepository.findByUsuarioEmailOrderByDataDesc(email);

        BigDecimal receitas = BigDecimal.ZERO;
        BigDecimal despesas = BigDecimal.ZERO;
        Map<String, BigDecimal> categorias = new HashMap<>();

        for (Transacao t : transacoes) {
            if ("income".equals(t.getTipo())) {
                receitas = receitas.add(t.getValor());
            } else {
                despesas = despesas.add(t.getValor());
                String cat = t.getCategoria() != null ? t.getCategoria() : "outros";
                categorias.merge(cat, t.getValor(), BigDecimal::add);
            }
        }

        BigDecimal saldo = receitas.subtract(despesas);
        return new ResumoResponse(new ResumoResponse.SummaryData(
                usuario.getSalario(), receitas, despesas, saldo, categorias
        ));
    }
}
