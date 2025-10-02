package org.com.imaapi.application.useCaseImpl.disponibilidade;

import org.com.imaapi.application.dto.disponibilidade.input.DisponibilidadeInput;
import org.com.imaapi.application.dto.disponibilidade.output.DisponibilidadeOutput;
import org.com.imaapi.application.useCase.disponibilidade.CriarDisponibilidadeUseCase;
import org.com.imaapi.domain.model.Disponibilidade;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.DisponibilidadeRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriarDisponibilidadeUseCaseImpl implements CriarDisponibilidadeUseCase {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CriarDisponibilidadeUseCaseImpl.class);
    
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final VoluntarioRepository voluntarioRepository;
    private final DisponibilidadeUtil disponibilidadeUtil;
    
    public CriarDisponibilidadeUseCaseImpl(
            DisponibilidadeRepository disponibilidadeRepository,
            VoluntarioRepository voluntarioRepository,
            DisponibilidadeUtil disponibilidadeUtil) {
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.voluntarioRepository = voluntarioRepository;
        this.disponibilidadeUtil = disponibilidadeUtil;
    }
    
    @Override
    @Transactional
    public DisponibilidadeOutput criarDisponibilidade(DisponibilidadeInput disponibilidadeInput) {
        // Validações de entrada
        disponibilidadeUtil.validarDisponibilidadeInput(disponibilidadeInput);
        
        LOGGER.info("Criando nova disponibilidade para usuário ID: {} em {}", 
                   disponibilidadeInput.getUsuarioId(), disponibilidadeInput.getDataHorario());
        
        // Buscar voluntário pelo ID do usuário
        Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(disponibilidadeInput.getUsuarioId());
        if (voluntario == null) {
            LOGGER.warn("Voluntário não encontrado para usuário ID: {}", disponibilidadeInput.getUsuarioId());
            throw new IllegalArgumentException("Voluntário não encontrado para o usuário ID: " + disponibilidadeInput.getUsuarioId());
        }
        
        // Converter e configurar disponibilidade
        Disponibilidade disponibilidade = disponibilidadeUtil.converterParaDisponibilidade(disponibilidadeInput);
        disponibilidade.setVoluntario(voluntario);
        
        // Salvar
        Disponibilidade disponibilidadeSalva = disponibilidadeRepository.save(disponibilidade);
        
        LOGGER.info("Disponibilidade criada com sucesso: ID={}, Voluntário={}", 
                   disponibilidadeSalva.getIdDisponibilidade(), voluntario.getIdVoluntario());
        
        // Converter para output e retornar
        return disponibilidadeUtil.converterParaDisponibilidadeOutput(disponibilidadeSalva);
    }
}