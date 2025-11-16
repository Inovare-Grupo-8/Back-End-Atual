package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarConsultaPorIdUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarConsultaPorIdUseCaseImpl implements BuscarConsultaPorIdUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarConsultaPorIdUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public ConsultaOutput buscarConsultaPorId(Integer id) {
        logger.info("Buscando consulta por ID: {}", id);

        try {
            if (id == null) {
                throw new IllegalArgumentException("ID da consulta é obrigatório");
            }

            Usuario usuarioLogado = consultaUtil.getUsuarioLogado();
            Integer userId = usuarioLogado.getIdUsuario();

            logger.info("Buscando consulta com ID: {} para usuário: {}", id, userId);

            Consulta consulta = consultaRepository.findById(id).orElse(null);

            if (consulta == null) {
                logger.error("Consulta não encontrada com ID: {}", id);
                return null;
            }

            // Verificar se o usuário logado tem permissão para ver a consulta
            boolean temPermissao = consulta.getAssistido().getIdUsuario().equals(userId) || 
                                  consulta.getVoluntario().getIdUsuario().equals(userId);

            if (!temPermissao) {
                logger.error("Usuário {} não tem permissão para acessar a consulta {}", userId, id);
                throw new SecurityException("Usuário não tem permissão para acessar esta consulta");
            }

            logger.info("Consulta encontrada com ID: {} para usuário: {}", id, userId);
            return consultaUtil.mapConsultaToOutput(consulta);

        } catch (Exception e) {
            logger.error("Erro ao buscar consulta por ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Erro ao buscar consulta por ID", e);
        }
    }
}