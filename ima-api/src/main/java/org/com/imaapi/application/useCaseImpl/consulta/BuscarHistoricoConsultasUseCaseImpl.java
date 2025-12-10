package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.BuscarHistoricoConsultasUseCase;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
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
    public List<ConsultaOutput> buscarHistoricoConsultas(Integer userId, String userType) {
        logger.info("Buscando histórico de consultas para o usuário ID: {} e perfil: {}", userId, userType);

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
            ConsultaOutput output = new ConsultaOutput();
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
            
            // Adicionar dados de especialidade (tanto nome quanto objeto completo)
            if (consulta.getEspecialidade() != null) {
                output.setIdEspecialidade(consulta.getEspecialidade().getIdEspecialidade());
                output.setNomeEspecialidade(consulta.getEspecialidade().getNome());
                output.setEspecialidade(consulta.getEspecialidade());
            }
            
            // Adicionar dados do assistido (tanto nome quanto objeto completo)
            if (consulta.getAssistido() != null) {
                output.setIdAssistido(consulta.getAssistido().getIdUsuario());
                if (consulta.getAssistido().getFicha() != null) {
                    output.setNomeAssistido(consulta.getAssistido().getFicha().getNome() + " " + 
                        (consulta.getAssistido().getFicha().getSobrenome() != null ? consulta.getAssistido().getFicha().getSobrenome() : ""));
                }
                output.setAssistido(consulta.getAssistido());
            }
            
            // Adicionar dados do voluntário (tanto nome quanto objeto completo)
            if (consulta.getVoluntario() != null) {
                output.setIdVoluntario(consulta.getVoluntario().getIdUsuario());
                if (consulta.getVoluntario().getFicha() != null) {
                    output.setNomeVoluntario(consulta.getVoluntario().getFicha().getNome() + " " + 
                        (consulta.getVoluntario().getFicha().getSobrenome() != null ? consulta.getVoluntario().getFicha().getSobrenome() : ""));
                }
                output.setVoluntario(consulta.getVoluntario());
            }

            return output;
        }).collect(Collectors.toList());
    }
}