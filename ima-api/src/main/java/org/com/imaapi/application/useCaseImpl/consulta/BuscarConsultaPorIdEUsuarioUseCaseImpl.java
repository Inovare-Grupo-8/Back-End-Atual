package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarConsultaPorIdEUsuarioUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuscarConsultaPorIdEUsuarioUseCaseImpl implements BuscarConsultaPorIdEUsuarioUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarConsultaPorIdEUsuarioUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public ConsultaOutput buscarConsultaPorIdEUsuario(Integer consultaId, String user) {
        logger.info("Buscando consulta com ID {} para o usuário tipo {}", consultaId, user);

        try {
            consultaUtil.validarTipoUsuario(user);

            // Obter o usuário logado
            Usuario usuarioLogado = consultaUtil.getUsuarioLogado();
            if (usuarioLogado == null) {
                logger.error("Usuário não encontrado: {}", user);
                return null;
            }

            // Buscar a consulta pelo ID
            Consulta consulta = consultaRepository.findById(consultaId).orElse(null);

            if (consulta == null) {
                logger.error("Consulta não encontrada com ID: {}", consultaId);
                return null;
            }

            // Verificar se o usuário tem permissão para ver essa consulta
            boolean autorizado = false;
            if (user.equalsIgnoreCase("voluntario") && 
                    usuarioLogado.getIdUsuario().equals(consulta.getVoluntario().getIdUsuario())) {
                autorizado = true;
            } else if (user.equalsIgnoreCase("assistido") && 
                       usuarioLogado.getIdUsuario().equals(consulta.getAssistido().getIdUsuario())) {
                autorizado = true;
            }

            if (!autorizado) {
                logger.error("Usuário {} não tem permissão para acessar a consulta {}", user, consultaId);
                return null;
            }

            logger.info("Consulta encontrada com ID {} para usuário {}", consultaId, user);
            return consultaUtil.mapConsultaToOutput(consulta);

        } catch (Exception e) {
            logger.error("Erro ao buscar consulta por ID {} e usuário {}: {}", consultaId, user, e.getMessage());
            throw new RuntimeException("Erro ao buscar consulta por ID e usuário", e);
        }
    }
}