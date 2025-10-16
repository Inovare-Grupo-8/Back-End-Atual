package org.com.imaapi.application.useCase.avaliacao;

import org.com.imaapi.application.dto.consulta.input.AvaliacaoInput;
import org.com.imaapi.application.dto.consulta.input.FeedbackInput;
import org.com.imaapi.domain.model.AvaliacaoConsulta;
import org.com.imaapi.domain.model.FeedbackConsulta;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoFeedbackUseCase {
    // Métodos para Avaliação
    AvaliacaoConsulta criarAvaliacao(Integer idConsulta, AvaliacaoInput avaliacaoInput);
    Optional<AvaliacaoConsulta> buscarAvaliacaoPorId(Integer idAvaliacao);
    List<AvaliacaoConsulta> buscarAvaliacoesPorConsulta(Integer idConsulta);
    
    // Métodos para Feedback
    FeedbackConsulta criarFeedback(Integer idConsulta, FeedbackInput feedbackInput);
    Optional<FeedbackConsulta> buscarFeedbackPorId(Integer idFeedback);
    List<FeedbackConsulta> buscarFeedbacksPorConsulta(Integer idConsulta);
}