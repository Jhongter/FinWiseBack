package com.exemplo.usuario.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal salario = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean emailVerificado = false;

    @Column
    private String tokenConfirmacao;

    protected Usuario() {}

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public BigDecimal getSalario() { return salario; }
    public boolean isEmailVerificado() { return emailVerificado; }
    public String getTokenConfirmacao() { return tokenConfirmacao; }

    public void atualizarDados(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void setSenha(String senha) { this.senha = senha; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }
    public void setEmailVerificado(boolean emailVerificado) { this.emailVerificado = emailVerificado; }
    public void setTokenConfirmacao(String tokenConfirmacao) { this.tokenConfirmacao = tokenConfirmacao; }
}
