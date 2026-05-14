package com.exemplo.usuario.service;

import com.exemplo.usuario.domain.Transacao;
import com.exemplo.usuario.domain.Usuario;
import com.exemplo.usuario.dto.*;
import com.exemplo.usuario.repository.TransacaoRepository;
import com.exemplo.usuario.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public TransacaoService(TransacaoRepository transacaoRepository,
                            UsuarioRepository usuarioRepository) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private String emailAtual() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private Usuario usuarioAtual() {
        return usuarioRepository.findByEmail(emailAtual())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    /** Lista todas as transações do usuário logado */
    public TransacoesListResponse listar() {
        List<TransacaoResponse> lista = transacaoRepository
                .findByUsuarioEmailOrderByDataDesc(emailAtual())
                .stream()
                .map(TransacaoResponse::de)
                .collect(Collectors.toList());
        return new TransacoesListResponse(lista);
    }

    /** Cria uma nova transação */
    @Transactional
    public TransacaoResponse criar(TransacaoRequest req) {
        Usuario usuario = usuarioAtual();
        Transacao t = new Transacao(
                req.description(), req.amount(), req.type(),
                req.category(), req.date(), usuario
        );
        return TransacaoResponse.de(transacaoRepository.save(t));
    }

    /** Remove uma transação (só se pertencer ao usuário logado) */
    @Transactional
    public void remover(Long id) {
        Transacao t = transacaoRepository.findByIdAndUsuarioEmail(id, emailAtual())
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada"));
        transacaoRepository.delete(t);
    }

    /** Retorna o resumo financeiro (salário, receitas, despesas, saldo, categorias) */
    public ResumoResponse resumo() {
        Usuario usuario = usuarioAtual();
        List<Transacao> transacoes = transacaoRepository.findByUsuarioEmailOrderByDataDesc(emailAtual());

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

    /** Atualiza o salário do usuário */
    @Transactional
    public void atualizarSalario(SalarioRequest req) {
        Usuario usuario = usuarioAtual();
        usuario.setSalario(req.amount());
        usuarioRepository.save(usuario);
    }
}
