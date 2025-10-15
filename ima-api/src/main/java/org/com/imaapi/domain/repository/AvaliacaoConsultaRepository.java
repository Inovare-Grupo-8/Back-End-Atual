package org.com.imaapi.domain.repository;

import org.com.imaapi.domain.model.AvaliacaoConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvaliacaoConsultaRepository extends JpaRepository<AvaliacaoConsulta, Integer> {
    List<AvaliacaoConsulta> findByConsulta_Voluntario_IdUsuario(Integer idVoluntario);
    List<AvaliacaoConsulta> findByConsulta_Assistido_IdUsuario(Integer idAssistido);
    Optional<AvaliacaoConsulta> findByConsultaIdConsulta(Integer idConsulta);
}
