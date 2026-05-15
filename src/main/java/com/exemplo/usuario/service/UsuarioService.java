package com.exemplo.usuario.service;

import com.exemplo.usuario.domain.Usuario;
import com.exemplo.usuario.dto.*;
import com.exemplo.usuario.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponse criar(UsuarioRequest request) {
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        String senhaHash = passwordEncoder.encode(request.senha());
        Usuario usuario = new Usuario(request.nome(), request.email(), senhaHash);

        // Confirma e-mail automaticamente (sem verificação por e-mail)
        usuario.setEmailVerificado(true);
        usuario.setTokenConfirmacao(null);

        usuarioRepository.save(usuario);

        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResponse(u.getId(), u.getNome(), u.getEmail()))
                .toList();
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail());
    }

    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioRequest request) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        u.atualizarDados(request.nome(), request.email(), passwordEncoder.encode(request.senha()));
        return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail());
    }

    @Transactional
    public void remover(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public void trocarSenha(TrocaSenhaRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.senhaAtual(), usuario.getSenha())) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        usuario.setSenha(passwordEncoder.encode(request.novaSenha()));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void confirmarEmail(String token) {
        // Mantido para não quebrar rotas existentes, mas não faz nada
    }
}
