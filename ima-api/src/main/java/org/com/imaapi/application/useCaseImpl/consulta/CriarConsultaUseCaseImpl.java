package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.useCase.consulta.CriarConsultaUseCase;
import org.com.imaapi.application.dto.consulta.input.ConsultaInput;
import org.com.imaapi.application.dto.consulta.output.ConsultaSimpleOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.EspecialidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.com.imaapi.application.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarConsultaUseCaseImpl implements CriarConsultaUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CriarConsultaUseCaseImpl.class);

    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final ConsultaUtil consultaUtil;

    @Autowired
    public CriarConsultaUseCaseImpl(ConsultaRepository consultaRepository,
                                    UsuarioRepository usuarioRepository,
                                    EspecialidadeRepository especialidadeRepository,
                                    ConsultaUtil consultaUtil) {
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
        this.especialidadeRepository = especialidadeRepository;
        this.consultaUtil = consultaUtil;
    }

    @Override
    public ConsultaSimpleOutput criarConsulta(ConsultaInput input) {
        logger.info("Iniciando criação de consulta");

        try {
            // Validações de entrada
            validarInput(input);

            Consulta consulta = new Consulta();
            consulta.setHorario(input.getHorario());
            consulta.setStatus(input.getStatus());
            consulta.setModalidade(input.getModalidade());
            consulta.setLocal(input.getLocal());
            consulta.setObservacoes(input.getObservacoes());

            // Buscar entidades pelos IDs com mensagens específicas
            consulta.setEspecialidade(especialidadeRepository.findById(input.getIdEspecialidade())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Especialidade com ID %d não encontrada", input.getIdEspecialidade()))));

            consulta.setAssistido(usuarioRepository.findById(input.getIdAssistido())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Assistido com ID %d não encontrado", input.getIdAssistido()))));

            consulta.setVoluntario(usuarioRepository.findById(input.getIdVoluntario())
                    .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Voluntário com ID %d não encontrado", input.getIdVoluntario()))));

            Consulta consultaSalva = consultaRepository.save(consulta);
            logger.info("Consulta criada com sucesso: ID = {}", consultaSalva.getIdConsulta());

            return consultaUtil.mapConsultaToSimpleOutput(consultaSalva);

        } catch (Exception e) {
            logger.error("Erro ao criar consulta: {}", e.getMessage());
            // Preserve specific exceptions so handler can translate them to proper HTTP codes
            if (e instanceof ResourceNotFoundException) {
                throw (ResourceNotFoundException) e;
            }
            if (e instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e;
            }
            throw new RuntimeException("Erro ao criar consulta", e);
        }
    }

    // Valida os dados de entrada para criação da consulta
    private void validarInput(ConsultaInput input) {
        if (input == null) {
            throw new IllegalArgumentException("ConsultaInput é obrigatório");
        }

        // Usar métodos do ConsultaUtil para validações
        consultaUtil.validarId(input.getIdEspecialidade());
        consultaUtil.validarId(input.getIdAssistido());
        consultaUtil.validarId(input.getIdVoluntario());

        if (input.getHorario() == null) {
            throw new IllegalArgumentException("Horário da consulta é obrigatório");
        }

        if (input.getStatus() == null) {
            throw new IllegalArgumentException("Status da consulta é obrigatório");
        }

        if (input.getModalidade() == null) {
            throw new IllegalArgumentException("Modalidade da consulta é obrigatória");
        }

        logger.debug("Validação de entrada concluída com sucesso");
    }
}
