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
import java.util.stream.Collectors;

@Service
public class BuscarHistoricoConsultasUseCaseImpl implements BuscarHistoricoConsultasUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(BuscarHistoricoConsultasUseCaseImpl.class);

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public List<ConsultaSimpleOutput> buscarHistoricoConsultas(Integer userId) {
        logger.info("Buscando histórico de consultas para o usuário ID: {}", userId);

        // Se userId não for fornecido, usa ID padrão 1 para teste
        Integer userIdFinal = (userId != null && userId > 0) ? userId : 1;

        // Status que representam consultas concluídas/históricas
        List<StatusConsulta> statusFinalizados = Arrays.asList(
                StatusConsulta.REALIZADA,
                StatusConsulta.CANCELADA
        );

        logger.debug("Buscando consultas finalizadas para usuário ID: {} com status: {}", userIdFinal, statusFinalizados);

        // Buscar consultas do usuário como assistido
        List<Consulta> consultasAssistido = consultaRepository.findByAssistido_IdUsuarioAndStatusIn(userIdFinal, statusFinalizados);
        
        // Buscar consultas do usuário como voluntário
        List<Consulta> consultasVoluntario = consultaRepository.findByVoluntario_IdUsuarioAndStatusIn(userIdFinal, statusFinalizados);
        
        // Combinar as duas listas
        List<Consulta> consultas = new java.util.ArrayList<>();
        consultas.addAll(consultasAssistido);
        consultas.addAll(consultasVoluntario);
        
        // Remover duplicatas e ordenar por data (mais recentes primeiro)
        consultas = consultas.stream()
                .distinct()
                .sorted((c1, c2) -> c2.getHorario().compareTo(c1.getHorario())) // Ordem decrescente
                .collect(Collectors.toList());

        logger.info("Encontradas {} consultas no histórico para usuário ID: {} ({}   como assistido, {} como voluntário)", 
                   consultas.size(), userIdFinal, consultasAssistido.size(), consultasVoluntario.size());

        if (consultas.isEmpty()) {
            return Collections.emptyList();
        }

        // Mapear para DTO simples
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
            return output;
        }).collect(Collectors.toList());
    }
}