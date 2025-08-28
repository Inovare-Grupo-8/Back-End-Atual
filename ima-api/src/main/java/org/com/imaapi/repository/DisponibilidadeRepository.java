package org.com.imaapi.repository;

import org.com.imaapi.domain.model.usuario.Disponibilidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Integer> {
}
