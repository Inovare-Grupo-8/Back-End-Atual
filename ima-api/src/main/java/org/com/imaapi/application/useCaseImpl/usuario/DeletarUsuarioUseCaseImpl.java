package org.com.imaapi.application.useCaseImpl.usuario;

import org.com.imaapi.application.useCase.usuario.DeletarUsuarioUseCase;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.repository.FichaRepository;
import org.com.imaapi.domain.repository.TelefoneRepository;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.VoluntarioRepository;
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

    @Override
    @Transactional
    public void executar(Integer id) {
        LOGGER.info("Iniciando deleção de usuário id={}", id);

        // remover voluntário (se existir)
        try {
            Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(id);
            if (voluntario != null) {
                voluntarioRepository.delete(voluntario);
                LOGGER.debug("Voluntário removido para usuario id={}", id);
            }
        } catch (Exception e) {
            LOGGER.warn("Falha ao remover voluntário para usuario id={}: {}", id, e.getMessage());
        }

        // obter id da ficha sem carregar entidade
        Integer fichaId = usuarioRepository.findFichaIdByUsuarioId(id);
        if (fichaId != null) {
            LOGGER.info("Deletando ficha id={} (deve propagar para usuario/telefone via DB)", fichaId);
            fichaRepository.deleteById(fichaId);
            LOGGER.info("Deleção concluída para usuario id={}", id);
            return;
        }

        // fallback: deletar usuário diretamente
        LOGGER.info("Nenhuma ficha associada encontrada, deletando usuario id={}", id);
        usuarioRepository.deleteById(id);
        LOGGER.info("Deleção concluída para usuario id={}", id);
    }
}
