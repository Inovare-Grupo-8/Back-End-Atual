package org.com.imaapi.domain.repository;

import org.com.imaapi.application.usecase.consulta.FeedbackConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackConsultaRepository extends JpaRepository<FeedbackConsulta, Integer> {
    List<FeedbackConsulta> findByConsulta_Voluntario_IdUsuario(Integer idVoluntario);

    List<FeedbackConsulta> findByConsulta_Assistido_IdUsuario(Integer idAssistido);
}
