package com.exemplo.usuario.repository;

import com.exemplo.usuario.domain.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {
    List<Meta> findByUsuarioEmailOrderByCriadoEmDesc(String email);
    Optional<Meta> findByIdAndUsuarioEmail(Long id, String email);
}
