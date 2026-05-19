package com.exemplo.usuario.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 300)
    private String descricao;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorAlvo;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorAtual = BigDecimal.ZERO;

    @Column(nullable = false, length = 20)
    private String status = "em_andamento"; // em_andamento | concluida | pausada

    @Column
    private LocalDate prazo;

    @Column(length = 10)
    private String emoji = "🎯";

    @Column(length = 20)
    private String cor = "#10b981";

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime atualizadoEm = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    protected Meta() {}

    public Meta(String titulo, String descricao, BigDecimal valorAlvo, BigDecimal valorAtual,
                LocalDate prazo, String emoji, String cor, Usuario usuario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.valorAlvo = valorAlvo;
        this.valorAtual = valorAtual != null ? valorAtual : BigDecimal.ZERO;
        this.prazo = prazo;
        this.emoji = emoji != null ? emoji : "🎯";
        this.cor = cor != null ? cor : "#10b981";
        this.usuario = usuario;
    }

    // ── Getters ───────────────────────────────────────────
    public Long getId()               { return id; }
    public String getTitulo()         { return titulo; }
    public String getDescricao()      { return descricao; }
    public BigDecimal getValorAlvo()  { return valorAlvo; }
    public BigDecimal getValorAtual() { return valorAtual; }
    public String getStatus()         { return status; }
    public LocalDate getPrazo()       { return prazo; }
    public String getEmoji()          { return emoji; }
    public String getCor()            { return cor; }
    public LocalDateTime getCriadoEm()    { return criadoEm; }
    public LocalDateTime getAtualizadoEm(){ return atualizadoEm; }
    public Usuario getUsuario()       { return usuario; }

    // ── Setters de negócio ────────────────────────────────
    public void adicionarValor(BigDecimal valor) {
        this.valorAtual = this.valorAtual.add(valor);
        if (this.valorAtual.compareTo(this.valorAlvo) >= 0) {
            this.status = "concluida";
        }
        this.atualizadoEm = LocalDateTime.now();
    }

    public void setStatus(String status) {
        this.status = status;
        this.atualizadoEm = LocalDateTime.now();
    }
}
