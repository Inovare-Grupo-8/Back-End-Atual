package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.AdicionarFeedbackConsultaUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.FeedbackConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.com.imaapi.domain.repository.FeedbackConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdicionarFeedbackConsultaUseCaseImpl implements AdicionarFeedbackConsultaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(AdicionarFeedbackConsultaUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private FeedbackConsultaRepository feedbackRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public ConsultaOutput adicionarFeedback(Integer consultaId, String feedback) {
        logger.info("Adicionando feedback à consulta {}", consultaId);

        try {
            // Validações usando ConsultaUtil
            consultaUtil.validarId(consultaId);
            consultaUtil.validarStringObrigatoria(feedback, "Feedback");

            // Buscar a consulta
            Consulta consulta = consultaRepository.findById(consultaId)
                    .orElseThrow(() -> {
                        logger.error("Consulta não encontrada com ID: {}", consultaId);
                        return new RuntimeException("Consulta não encontrada");
                    });

            // Criar e salvar o feedback
            FeedbackConsulta feedbackConsulta = new FeedbackConsulta();
            feedbackConsulta.setConsulta(consulta);
            feedbackConsulta.setComentario(feedback);
            feedbackConsulta.setDtFeedback(LocalDateTime.now());

            feedbackRepository.save(feedbackConsulta);
            logger.info("Feedback salvo com sucesso para consulta {}", consultaId);

            // Atualizar status da consulta
            consulta.setFeedbackStatus("ENVIADO");
            consultaRepository.save(consulta);

            // Retornar o output
            return consultaUtil.mapConsultaToOutput(consulta);

        } catch (Exception e) {
            logger.error("Erro ao adicionar feedback à consulta {}: {}", consultaId, e.getMessage());
            throw new RuntimeException("Erro ao adicionar feedback", e);
        }
    }
}
