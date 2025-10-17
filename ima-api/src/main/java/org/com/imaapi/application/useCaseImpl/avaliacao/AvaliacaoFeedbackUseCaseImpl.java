package org.com.imaapi.application.useCaseImpl.avaliacao;

import org.com.imaapi.application.dto.consulta.input.AvaliacaoInput;
import org.com.imaapi.application.dto.consulta.input.FeedbackInput;
import org.com.imaapi.application.useCase.avaliacao.AvaliacaoFeedbackUseCase;
import org.com.imaapi.domain.model.AvaliacaoConsulta;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.FeedbackConsulta;
import org.com.imaapi.domain.repository.AvaliacaoConsultaRepository;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.com.imaapi.domain.repository.FeedbackConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoFeedbackUseCaseImpl implements AvaliacaoFeedbackUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AvaliacaoFeedbackUseCaseImpl.class);

    private final AvaliacaoConsultaRepository avaliacaoRepository;
    private final FeedbackConsultaRepository feedbackRepository;
    private final ConsultaRepository consultaRepository;

    public AvaliacaoFeedbackUseCaseImpl(
            AvaliacaoConsultaRepository avaliacaoRepository,
            FeedbackConsultaRepository feedbackRepository,
            ConsultaRepository consultaRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.feedbackRepository = feedbackRepository;
        this.consultaRepository = consultaRepository;
    }

    @Override
    @Transactional
    public AvaliacaoConsulta criarAvaliacao(Integer idConsulta, AvaliacaoInput avaliacaoInput) {
        LOGGER.info("Criando avaliação para consulta com ID: {}", idConsulta);
        
        Optional<Consulta> consultaOptional = consultaRepository.findById(idConsulta);
        if (consultaOptional.isEmpty()) {
            LOGGER.error("Consulta não encontrada com ID: {}", idConsulta);
            return null;
        }
        
        Consulta consulta = consultaOptional.get();
        
        AvaliacaoConsulta avaliacao = new AvaliacaoConsulta();
        avaliacao.setConsulta(consulta);
        avaliacao.setNota(avaliacaoInput.getNota());
        avaliacao.setDtAvaliacao(LocalDateTime.now());
        
        return avaliacaoRepository.save(avaliacao);
    }

    @Override
    public Optional<AvaliacaoConsulta> buscarAvaliacaoPorId(Integer idAvaliacao) {
        LOGGER.info("Buscando avaliação com ID: {}", idAvaliacao);
        return avaliacaoRepository.findById(idAvaliacao);
    }

    @Override
    public List<AvaliacaoConsulta> buscarAvaliacoesPorConsulta(Integer idConsulta) {
        LOGGER.info("Buscando avaliações para consulta com ID: {}", idConsulta);
        Optional<AvaliacaoConsulta> avaliacaoOptional = avaliacaoRepository.findByConsultaIdConsulta(idConsulta);
        return avaliacaoOptional.map(Collections::singletonList).orElse(Collections.emptyList());
    }

    @Override
    @Transactional
    public FeedbackConsulta criarFeedback(Integer idConsulta, FeedbackInput feedbackInput) {
        LOGGER.info("Criando feedback para consulta com ID: {}", idConsulta);
        
        Optional<Consulta> consultaOptional = consultaRepository.findById(idConsulta);
        if (consultaOptional.isEmpty()) {
            LOGGER.error("Consulta não encontrada com ID: {}", idConsulta);
            return null;
        }
        
        Consulta consulta = consultaOptional.get();
        
        FeedbackConsulta feedback = new FeedbackConsulta();
        feedback.setConsulta(consulta);
        feedback.setComentario(feedbackInput.getComentario());
        feedback.setDtFeedback(LocalDateTime.now());
        
        return feedbackRepository.save(feedback);
    }

    @Override
    public Optional<FeedbackConsulta> buscarFeedbackPorId(Integer idFeedback) {
        LOGGER.info("Buscando feedback com ID: {}", idFeedback);
        return feedbackRepository.findById(idFeedback);
    }

    @Override
    public List<FeedbackConsulta> buscarFeedbacksPorConsulta(Integer idConsulta) {
        LOGGER.info("Buscando feedbacks para consulta com ID: {}", idConsulta);
        Optional<FeedbackConsulta> feedbackOptional = feedbackRepository.findByConsultaIdConsulta(idConsulta);
        return feedbackOptional.map(Collections::singletonList).orElse(Collections.emptyList());
    }
}