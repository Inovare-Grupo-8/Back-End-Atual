package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarHistoricoConsultasUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BuscarHistoricoConsultasUseCaseImpl implements BuscarHistoricoConsultasUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarHistoricoConsultasUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public List<ConsultaOutput> buscarHistoricoConsultas(String user) {
        logger.info("Buscando histórico de consultas para o usuário tipo: {}", user);

        try {
            consultaUtil.validarTipoUsuario(user);

            // Obter o usuário logado
            Usuario usuarioLogado = consultaUtil.getUsuarioLogado();
            if (usuarioLogado == null) {
                logger.error("Usuário não encontrado: {}", user);
                return Collections.emptyList();
            }

            // Status que representam consultas concluídas/históricas
            List<StatusConsulta> statusConcluidos = consultaUtil.getStatusFinalizados();

            List<Consulta> consultas;
            if (user.equalsIgnoreCase("voluntario")) {
                consultas = consultaRepository.findByVoluntario_IdUsuarioAndStatusIn(
                        usuarioLogado.getIdUsuario(), statusConcluidos);
            } else if (user.equalsIgnoreCase("assistido")) {
                consultas = consultaRepository.findByAssistido_IdUsuarioAndStatusIn(
                        usuarioLogado.getIdUsuario(), statusConcluidos);
            } else {
                logger.error("Tipo de usuário não suportado: {}", user);
                return Collections.emptyList();
            }

            logger.info("Encontradas {} consultas no histórico para usuário tipo: {}", consultas.size(), user);

            return consultaUtil.mapConsultasToOutput(consultas);

        } catch (Exception e) {
            logger.error("Erro ao buscar histórico de consultas para usuário {}: {}", user, e.getMessage());
            throw new RuntimeException("Erro ao buscar histórico de consultas", e);
        }
    }
}