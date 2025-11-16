package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarConsultasRecentesUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BuscarConsultasRecentesUseCaseImpl implements BuscarConsultasRecentesUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarConsultasRecentesUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public List<ConsultaOutput> buscarConsultasRecentes(String user) {
        logger.info("Buscando consultas recentes para usuário tipo: {}", user);

        try {
            consultaUtil.validarTipoUsuario(user);

            Usuario usuarioLogado = consultaUtil.getUsuarioLogado();
            Integer userId = usuarioLogado.getIdUsuario();

            logger.info("Buscando consultas recentes para {} com ID: {}", user, userId);

            // Buscar todas as consultas do usuário
            List<Consulta> todasConsultasUsuario;
            if (user.equals("voluntario")) {
                todasConsultasUsuario = consultaRepository.findByVoluntario_IdUsuario(userId);
            } else {
                todasConsultasUsuario = consultaRepository.findByAssistido_IdUsuario(userId);
            }

            logger.info("Total de consultas encontradas para {} com ID {}: {}", 
                       user, userId, todasConsultasUsuario.size());

            if (!todasConsultasUsuario.isEmpty()) {
                Map<StatusConsulta, Long> statusCount = todasConsultasUsuario.stream()
                        .collect(Collectors.groupingBy(Consulta::getStatus, Collectors.counting()));
                logger.info("Status das consultas encontradas: {}", statusCount);
            }

            // Filtrar consultas finalizadas
            List<Consulta> consultasFinalizadas = todasConsultasUsuario.stream()
                    .filter(consulta -> consultaUtil.getStatusFinalizados().contains(consulta.getStatus()))
                    .collect(Collectors.toList());

            List<Consulta> consultasParaMostrar;

            if (consultasFinalizadas.size() >= 3) {
                consultasParaMostrar = consultasFinalizadas;
                logger.info("Mostrando apenas consultas finalizadas: {}", consultasFinalizadas.size());
            } else {
                consultasParaMostrar = todasConsultasUsuario;
                logger.info("Mostrando todas as consultas disponíveis: {}", todasConsultasUsuario.size());
            }

            // Ordenar e limitar a 3
            List<Consulta> consultasOrdenadas = consultasParaMostrar.stream()
                    .sorted(Comparator.comparing(Consulta::getHorario).reversed())
                    .limit(3)
                    .collect(Collectors.toList());

            logger.info("Retornando {} consultas recentes para {} com ID: {}", 
                       consultasOrdenadas.size(), user, userId);

            return consultaUtil.mapConsultasToOutput(consultasOrdenadas);

        } catch (Exception e) {
            logger.error("Erro ao buscar consultas recentes para usuário {}: {}", user, e.getMessage());
            throw new RuntimeException("Erro ao buscar consultas recentes", e);
        }
    }
}