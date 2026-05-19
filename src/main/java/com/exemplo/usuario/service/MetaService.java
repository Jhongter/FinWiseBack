package com.exemplo.usuario.service;

import com.exemplo.usuario.domain.Meta;
import com.exemplo.usuario.domain.Usuario;
import com.exemplo.usuario.dto.*;
import com.exemplo.usuario.repository.MetaRepository;
import com.exemplo.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetaService implements IMetaService {

    private final MetaRepository metaRepository;
    private final UsuarioRepository usuarioRepository;

    public MetaService(MetaRepository metaRepository, UsuarioRepository usuarioRepository) {
        this.metaRepository = metaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario buscarUsuario(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    private Meta buscarMeta(Long id, String email) {
        return metaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new IllegalArgumentException("Meta não encontrada"));
    }

    @Override
    public List<MetaResponse> listar(String email) {
        return metaRepository.findByUsuarioEmailOrderByCriadoEmDesc(email)
                .stream()
                .map(MetaResponse::de)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MetaResponse criar(String email, MetaRequest req) {
        Usuario usuario = buscarUsuario(email);
        Meta meta = new Meta(
                req.title(),
                req.description(),
                req.targetAmount(),
                req.currentAmount(),
                req.deadline(),
                req.emoji(),
                req.color(),
                usuario
        );
        return MetaResponse.de(metaRepository.save(meta));
    }

    @Override
    @Transactional
    public MetaResponse adicionarValor(String email, Long id, MetaAdicionarValorRequest req) {
        Meta meta = buscarMeta(id, email);
        meta.adicionarValor(req.amount());
        return MetaResponse.de(metaRepository.save(meta));
    }

    @Override
    @Transactional
    public MetaResponse atualizarStatus(String email, Long id, MetaStatusRequest req) {
        Meta meta = buscarMeta(id, email);
        meta.setStatus(req.status());
        return MetaResponse.de(metaRepository.save(meta));
    }

    @Override
    @Transactional
    public void remover(String email, Long id) {
        Meta meta = buscarMeta(id, email);
        metaRepository.delete(meta);
    }
}
