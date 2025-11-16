package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.CancelarConsultaUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CancelarConsultaUseCaseImpl implements CancelarConsultaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CancelarConsultaUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public ConsultaOutput cancelarConsulta(Integer consultaId) {
        logger.info("Cancelando consulta com ID {}", consultaId);

        try {
            if (consultaId == null) {
                logger.error("ID da consulta é obrigatório");
                throw new IllegalArgumentException("ID da consulta é obrigatório");
            }

            // Buscar a consulta pelo ID
            Consulta consulta = consultaRepository.findById(consultaId)
                    .orElseThrow(() -> {
                        logger.error("Consulta não encontrada com ID: {}", consultaId);
                        return new RuntimeException("Consulta não encontrada");
                    });

            // Verificar se a consulta pode ser cancelada (não está já cancelada ou realizada)
            if (consulta.getStatus() == StatusConsulta.CANCELADA) {
                logger.warn("Tentativa de cancelar consulta já cancelada com ID: {}", consultaId);
                throw new IllegalStateException("Consulta já está cancelada");
            }

            if (consulta.getStatus() == StatusConsulta.REALIZADA) {
                logger.warn("Tentativa de cancelar consulta já realizada com ID: {}", consultaId);
                throw new IllegalStateException("Não é possível cancelar consulta já realizada");
            }

            // Verificar se o usuário logado tem permissão para cancelar esta consulta
            Usuario usuarioLogado = consultaUtil.getUsuarioLogado();
            if (usuarioLogado == null) {
                logger.error("Usuário não autenticado");
                throw new RuntimeException("Usuário não autenticado");
            }

            boolean podeCancel = false;
            if (consulta.getAssistido() != null && 
                usuarioLogado.getIdUsuario().equals(consulta.getAssistido().getIdUsuario())) {
                podeCancel = true;
            } else if (consulta.getVoluntario() != null && 
                       usuarioLogado.getIdUsuario().equals(consulta.getVoluntario().getIdUsuario())) {
                podeCancel = true;
            }

            if (!podeCancel) {
                logger.error("Usuário {} não tem permissão para cancelar a consulta {}", 
                           usuarioLogado.getIdUsuario(), consultaId);
                throw new RuntimeException("Usuário não tem permissão para cancelar esta consulta");
            }

            // Atualizar o status da consulta para CANCELADA
            consulta.setStatus(StatusConsulta.CANCELADA);

            // Salvar a consulta atualizada
            Consulta consultaCancelada = consultaRepository.save(consulta);
            logger.info("Consulta cancelada com sucesso. ID: {}", consultaId);

            // Retornar o output
            return consultaUtil.mapConsultaToOutput(consultaCancelada);

        } catch (Exception e) {
            logger.error("Erro ao cancelar consulta com ID {}: {}", consultaId, e.getMessage());
            throw new RuntimeException("Erro ao cancelar consulta", e);
        }
    }
}
