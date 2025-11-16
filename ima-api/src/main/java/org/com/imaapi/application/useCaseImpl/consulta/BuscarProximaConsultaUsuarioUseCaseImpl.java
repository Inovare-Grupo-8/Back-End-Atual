package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarProximaConsultaUsuarioUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BuscarProximaConsultaUsuarioUseCaseImpl implements BuscarProximaConsultaUsuarioUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarProximaConsultaUsuarioUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public ConsultaOutput buscarProximaConsulta(Integer idUsuario) {
        logger.info("Buscando próxima consulta para usuário ID: {}", idUsuario);

        try {
            // Buscar próximas consultas como voluntário e assistido
            List<Consulta> consultasVoluntario = consultaRepository
                    .findByVoluntario_IdUsuarioAndHorarioAfterOrderByHorarioAsc(idUsuario, LocalDateTime.now());

            List<Consulta> consultasAssistido = consultaRepository
                    .findByAssistido_IdUsuarioAndHorarioAfterOrderByHorarioAsc(idUsuario, LocalDateTime.now());

            // Encontrar a próxima consulta mais próxima
            Consulta proximaConsulta = null;

            if (!consultasVoluntario.isEmpty() && !consultasAssistido.isEmpty()) {
                proximaConsulta = consultasVoluntario.get(0).getHorario()
                        .isBefore(consultasAssistido.get(0).getHorario())
                        ? consultasVoluntario.get(0)
                        : consultasAssistido.get(0);
            } else if (!consultasVoluntario.isEmpty()) {
                proximaConsulta = consultasVoluntario.get(0);
            } else if (!consultasAssistido.isEmpty()) {
                proximaConsulta = consultasAssistido.get(0);
            }

            if (proximaConsulta == null) {
                logger.info("Nenhuma próxima consulta encontrada para usuário ID: {}", idUsuario);
                return null;
            }

            logger.info("Próxima consulta encontrada para usuário ID {}: Consulta ID {} em {}", 
                       idUsuario, proximaConsulta.getIdConsulta(), proximaConsulta.getHorario());

            return consultaUtil.mapConsultaToOutput(proximaConsulta);

        } catch (Exception e) {
            logger.error("Erro ao buscar próxima consulta para usuário ID {}: {}", idUsuario, e.getMessage());
            throw new RuntimeException("Erro ao buscar próxima consulta", e);
        }
    }
}