package com.exemplo.usuario.service;

import com.exemplo.usuario.domain.Usuario;
import com.exemplo.usuario.dto.UsuarioRequest;
import com.exemplo.usuario.dto.UsuarioResponse;
import com.exemplo.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public UsuarioResponse criar(UsuarioRequest request) {
        validarEmailDuplicado(request.email());

        String senhaCriptografada = passwordEncoder.encode(request.senha());
        Usuario usuario = new Usuario(request.nome(), request.email(), senhaCriptografada);
        Usuario salvo = usuarioRepository.save(usuario);

        return toResponse(salvo);
    }

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));

        return toResponse(usuario);
    }

    public UsuarioResponse atualizar(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));

        usuarioRepository.findByEmail(request.email())
                .filter(outro -> !outro.getId().equals(id))
                .ifPresent(outro -> {
                    throw new IllegalArgumentException("Já existe um usuário com este e-mail");
                });

        String senhaCriptografada = passwordEncoder.encode(request.senha());
        usuario.atualizarDados(request.nome(), request.email(), senhaCriptografada);
        Usuario atualizado = usuarioRepository.save(usuario);

        return toResponse(atualizado);
    }

    public void remover(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private void validarEmailDuplicado(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail");
        }
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
