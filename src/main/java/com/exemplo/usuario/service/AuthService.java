package com.exemplo.usuario.service;

import com.exemplo.usuario.domain.Usuario;
import com.exemplo.usuario.dto.LoginRequest;
import com.exemplo.usuario.dto.LoginResponse;
import com.exemplo.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Autentica o usuário e retorna um token JWT.
     * A mensagem de erro é genérica para não revelar se o e-mail existe ou não.
     */
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }

        String token = jwtService.gerarToken(usuario.getEmail());
        return new LoginResponse(token, usuario.getNome(), usuario.getEmail());
    }
}
