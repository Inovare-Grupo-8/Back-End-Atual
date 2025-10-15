package org.com.imaapi.domain.repository;

import org.com.imaapi.domain.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Integer> {
    boolean existsByNome(String nome);
    Optional<Especialidade> findByNome(String nome);
}
