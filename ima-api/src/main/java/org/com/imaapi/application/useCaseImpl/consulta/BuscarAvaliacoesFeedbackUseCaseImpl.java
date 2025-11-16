package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarAvaliacoesFeedbackUseCase;
import org.com.imaapi.domain.model.AvaliacaoConsulta;
import org.com.imaapi.domain.model.FeedbackConsulta;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.AvaliacaoConsultaRepository;
import org.com.imaapi.domain.repository.FeedbackConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuscarAvaliacoesFeedbackUseCaseImpl implements BuscarAvaliacoesFeedbackUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarAvaliacoesFeedbackUseCaseImpl.class);

    @Autowired
    private FeedbackConsultaRepository feedbackRepository;

    @Autowired
    private AvaliacaoConsultaRepository avaliacaoRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public Map<String, Object> buscarAvaliacoesFeedback(String user) {
        logger.info("Buscando avaliações e feedbacks para usuário tipo: {}", user);

        try {
            // Validação usando método do ConsultaUtil
            consultaUtil.validarTipoUsuario(user);

            Usuario usuarioLogado = consultaUtil.getUsuarioLogado();
            Integer userId = usuarioLogado.getIdUsuario();
            
            logger.debug("Buscando avaliações e feedbacks para usuário ID: {}", userId);

            List<FeedbackConsulta> feedbacks;
            List<AvaliacaoConsulta> avaliacoes;

            if (user.equals("voluntario")) {
                feedbacks = feedbackRepository.findByConsulta_Voluntario_IdUsuario(userId);
                avaliacoes = avaliacaoRepository.findByConsulta_Voluntario_IdUsuario(userId);
            } else {
                feedbacks = feedbackRepository.findByConsulta_Assistido_IdUsuario(userId);
                avaliacoes = avaliacaoRepository.findByConsulta_Assistido_IdUsuario(userId);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("feedbacks", feedbacks);
            response.put("avaliacoes", avaliacoes);

            logger.info("Encontrados {} feedbacks e {} avaliações para usuário {}", 
                       feedbacks.size(), avaliacoes.size(), user);

            return response;

        } catch (Exception e) {
            logger.error("Erro ao buscar avaliações e feedbacks para usuário {}: {}", user, e.getMessage());
            throw new RuntimeException("Erro ao buscar avaliações e feedbacks", e);
        }
    }
}