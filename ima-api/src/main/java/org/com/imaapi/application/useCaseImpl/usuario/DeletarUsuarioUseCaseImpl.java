package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.useCase.usuario.DeletarUsuarioUseCase;
import org.com.imaapi.domain.repository.FichaRepository;
import org.com.imaapi.domain.repository.TelefoneRepository;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.com.imaapi.domain.repository.UsuarioDeletionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarUsuarioUseCaseImpl implements DeletarUsuarioUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeletarUsuarioUseCaseImpl.class);
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private FichaRepository fichaRepository;

    @Autowired
    private UsuarioDeletionPort usuarioDeletionPort;

    @Override
    @Transactional
    public void executar(Integer id) {
        LOGGER.info("Iniciando deleção de usuário id={}", id);
        try {
            usuarioDeletionPort.deleteByIdCascade(id);
            LOGGER.info("Deleção concluída para usuario id={}", id);
        } catch (Exception e) {
            LOGGER.error("Erro ao deletar usuario id={}", id, e);
            throw e;
        }
    }
}
