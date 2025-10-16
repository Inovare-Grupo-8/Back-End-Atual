package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarProximasConsultasUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuscarProximasConsultasUseCaseImpl implements BuscarProximasConsultasUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarProximasConsultasUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaUtil consultaUtil;

    @Override
    public List<ConsultaOutput> buscarProximasConsultas(String user) {
        logger.info("Buscando próximas consultas para o usuário tipo: {}", user);

        try {
            consultaUtil.validarTipoUsuario(user);

            // Obter o usuário logado
            Usuario usuarioLogado = consultaUtil.getUsuarioLogado();
            if (usuarioLogado == null) {
                logger.error("Usuário não encontrado: {}", user);
                return Collections.emptyList();
            }

            LocalDateTime agora = LocalDateTime.now();
            
            // Status que representam consultas pendentes/futuras
            List<StatusConsulta> statusPendentes = consultaUtil.getStatusPendentes();

            List<Consulta> consultas;
            if (user.equalsIgnoreCase("voluntario")) {
                consultas = consultaRepository.findByVoluntario_IdUsuarioAndHorarioAfterOrderByHorarioAsc(
                        usuarioLogado.getIdUsuario(), agora);
            } else if (user.equalsIgnoreCase("assistido")) {
                consultas = consultaRepository.findByAssistido_IdUsuarioAndHorarioAfterOrderByHorarioAsc(
                        usuarioLogado.getIdUsuario(), agora);
            } else {
                logger.error("Tipo de usuário não suportado: {}", user);
                return Collections.emptyList();
            }

            // Filtrar apenas consultas com status pendentes e limitar a 3
            List<Consulta> consultasFiltradas = consultas.stream()
                    .filter(c -> statusPendentes.contains(c.getStatus()))
                    .limit(3)
                    .collect(Collectors.toList());

            logger.info("Encontradas {} próximas consultas para usuário tipo: {}", consultasFiltradas.size(), user);

            return consultaUtil.mapConsultasToOutput(consultasFiltradas);

        } catch (Exception e) {
            logger.error("Erro ao buscar próximas consultas para usuário {}: {}", user, e.getMessage());
            throw new RuntimeException("Erro ao buscar próximas consultas", e);
        }
    }
}