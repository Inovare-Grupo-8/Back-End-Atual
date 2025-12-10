package org.com.imaapi.domain.repository;

import org.com.imaapi.domain.model.FeedbackConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackConsultaRepository extends JpaRepository<FeedbackConsulta, Integer> {
    List<FeedbackConsulta> findByConsulta_Voluntario_IdUsuario(Integer idVoluntario);

    List<FeedbackConsulta> findByConsulta_Assistido_IdUsuario(Integer idAssistido);
    
    Optional<FeedbackConsulta> findByConsultaIdConsulta(Integer idConsulta);
}
