package org.com.imaapi.domain.repository;

import org.com.imaapi.application.usecase.consulta.AvaliacaoConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoConsultaRepository extends JpaRepository<AvaliacaoConsulta, Integer> {
    List<AvaliacaoConsulta> findByConsulta_Voluntario_IdUsuario(Integer idVoluntario);

    List<AvaliacaoConsulta> findByConsulta_Assistido_IdUsuario(Integer idAssistido);
}
