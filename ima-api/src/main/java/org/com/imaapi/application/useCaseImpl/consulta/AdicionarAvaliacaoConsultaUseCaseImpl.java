package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.application.useCase.consulta.AdicionarAvaliacaoConsultaUseCase;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.AvaliacaoConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.com.imaapi.domain.repository.AvaliacaoConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdicionarAvaliacaoConsultaUseCaseImpl implements AdicionarAvaliacaoConsultaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(AdicionarAvaliacaoConsultaUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private AvaliacaoConsultaRepository avaliacaoRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public ConsultaOutput adicionarAvaliacao(Integer consultaId, String avaliacao) {
        logger.info("Adicionando avaliação à consulta {}", consultaId);

        try {
            // Validações usando ConsultaUtil
            consultaUtil.validarId(consultaId);
            consultaUtil.validarStringObrigatoria(avaliacao, "Avaliação");

            // Converter avaliação para nota (1-5)
            int nota;
            try {
                nota = Integer.parseInt(avaliacao.trim());
                if (nota < 1 || nota > 5) {
                    logger.error("A nota deve estar entre 1 e 5");
                    throw new IllegalArgumentException("A nota deve estar entre 1 e 5");
                }
            } catch (NumberFormatException e) {
                logger.error("A avaliação deve ser um número entre 1 e 5");
                throw new IllegalArgumentException("A avaliação deve ser um número entre 1 e 5");
            }

            // Buscar a consulta
            Consulta consulta = consultaRepository.findById(consultaId)
                    .orElseThrow(() -> {
                        logger.error("Consulta não encontrada com ID: {}", consultaId);
                        return new RuntimeException("Consulta não encontrada");
                    });

            // Criar e salvar a avaliação
            AvaliacaoConsulta avaliacaoConsulta = new AvaliacaoConsulta();
            avaliacaoConsulta.setConsulta(consulta);
            avaliacaoConsulta.setNota(nota);
            avaliacaoConsulta.setDtAvaliacao(LocalDateTime.now());

            avaliacaoRepository.save(avaliacaoConsulta);
            logger.info("Avaliação salva com sucesso para consulta {}", consultaId);

            // Atualizar status da consulta
            consulta.setAvaliacaoStatus("ENVIADO");
            consultaRepository.save(consulta);

            // Retornar o output
            return consultaUtil.mapConsultaToOutput(consulta);

        } catch (Exception e) {
            logger.error("Erro ao adicionar avaliação à consulta {}: {}", consultaId, e.getMessage());
            throw new RuntimeException("Erro ao adicionar avaliação", e);
        }
    }
}
