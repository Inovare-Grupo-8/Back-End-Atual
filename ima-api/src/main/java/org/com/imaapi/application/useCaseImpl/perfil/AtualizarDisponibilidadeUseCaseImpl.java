package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.AtualizarDisponibilidadeUseCase;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class AtualizarDisponibilidadeUseCaseImpl implements AtualizarDisponibilidadeUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarDisponibilidadeUseCaseImpl.class);

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Override
    public boolean atualizarDisponibilidade(Integer usuarioId, Map<String, Object> disponibilidade) {
        LOGGER.info("Atualizando disponibilidade para o voluntário com ID de usuário: {}", usuarioId);
        Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuarioId);
        if (voluntario == null) {
            LOGGER.warn("Voluntário não encontrado para o ID de usuário: {}", usuarioId);
            return false;
        }

        // Lógica para atualizar disponibilidade (exemplo: atualizar campos específicos)
        // Exemplo: voluntario.setDisponibilidade(disponibilidade);

        voluntarioRepository.save(voluntario);
        LOGGER.info("Disponibilidade atualizada com sucesso para o voluntário com ID de usuário: {}", usuarioId);
        return true;
    }
}