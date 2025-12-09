package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarHistoricoConsultasUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaSimpleOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BuscarHistoricoConsultasUseCaseImpl implements BuscarHistoricoConsultasUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarHistoricoConsultasUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public List<ConsultaSimpleOutput> buscarHistoricoConsultas(Integer userId, String userType) {
        logger.info("Buscando histórico de consultas para o usuário ID: {} com perfil: {}", userId, userType);

        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId inválido para histórico de consultas");
        }

        String perfilNormalizado = userType != null ? userType.trim().toLowerCase() : "assistido";

        List<StatusConsulta> statusFinalizados = Arrays.asList(
                StatusConsulta.REALIZADA,
                StatusConsulta.CANCELADA
        );

        logger.debug("Buscando consultas finalizadas para usuário ID: {} com status: {} e perfil: {}",
                userId, statusFinalizados, perfilNormalizado);

        List<Consulta> consultasAssistido = Collections.emptyList();
        List<Consulta> consultasVoluntario = Collections.emptyList();

        switch (perfilNormalizado) {
            case "voluntario":
            case "voluntário":
            case "assistente-social":
            case "assistente_social":
                consultasVoluntario = consultaRepository.findByVoluntario_IdUsuarioAndStatusIn(userId, statusFinalizados);
                break;
            case "administrador":
                consultasAssistido = consultaRepository.findByAssistido_IdUsuarioAndStatusIn(userId, statusFinalizados);
                consultasVoluntario = consultaRepository.findByVoluntario_IdUsuarioAndStatusIn(userId, statusFinalizados);
                break;
            default:
                consultasAssistido = consultaRepository.findByAssistido_IdUsuarioAndStatusIn(userId, statusFinalizados);
                break;
        }

        List<Consulta> consultas = new java.util.ArrayList<>();
        consultas.addAll(consultasAssistido);
        consultas.addAll(consultasVoluntario);

        consultas = consultas.stream()
                .filter(Objects::nonNull)
                .distinct()
                .sorted((c1, c2) -> c2.getHorario().compareTo(c1.getHorario()))
                .collect(Collectors.toList());

        logger.info("Encontradas {} consultas no histórico para usuário ID: {} ({} como assistido, {} como voluntário)",
                consultas.size(), userId, consultasAssistido.size(), consultasVoluntario.size());

        if (consultas.isEmpty()) {
            return Collections.emptyList();
        }

        return consultas.stream().map(consulta -> {
            ConsultaSimpleOutput output = new ConsultaSimpleOutput();
            output.setIdConsulta(consulta.getIdConsulta());
            output.setHorario(consulta.getHorario());
            output.setStatus(consulta.getStatus().name());
            output.setModalidade(consulta.getModalidade().name());
            output.setLocal(consulta.getLocal());
            output.setObservacoes(consulta.getObservacoes());
            output.setFeedbackStatus(consulta.getFeedbackStatus());
            output.setAvaliacaoStatus(consulta.getAvaliacaoStatus());
            output.setCriadoEm(consulta.getCriadoEm());
            output.setAtualizadoEm(consulta.getAtualizadoEm());

            if (consulta.getAssistido() != null) {
                output.setAssistidoId(consulta.getAssistido().getIdUsuario());
                if (consulta.getAssistido().getFicha() != null) {
                    output.setAssistidoNome(consulta.getAssistido().getFicha().getNome());
                }
                if (consulta.getAssistido().getEmail() != null) {
                    output.setAssistidoEmail(consulta.getAssistido().getEmail());
                }
            }

            if (consulta.getVoluntario() != null) {
                output.setVoluntarioId(consulta.getVoluntario().getIdUsuario());
                if (consulta.getVoluntario().getFicha() != null) {
                    output.setVoluntarioNome(consulta.getVoluntario().getFicha().getNome());
                }
                if (consulta.getVoluntario().getEmail() != null) {
                    output.setVoluntarioEmail(consulta.getVoluntario().getEmail());
                }
            }

            if (consulta.getEspecialidade() != null) {
                output.setEspecialidadeId(consulta.getEspecialidade().getIdEspecialidade());
                output.setEspecialidadeNome(consulta.getEspecialidade().getNome());
            }
            return output;
        }).collect(Collectors.toList());
    }
}