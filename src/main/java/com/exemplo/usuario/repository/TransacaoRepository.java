package com.exemplo.usuario.repository;

import com.exemplo.usuario.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByUsuarioEmailOrderByDataDesc(String email);
    Optional<Transacao> findByIdAndUsuarioEmail(Long id, String email);
}
