package org.com.imaapi.infrastructure.gateway;

import org.com.imaapi.domain.repository.UsuarioDeletionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDeletionAdapter implements UsuarioDeletionPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDeletionAdapter.class);
    private final JdbcTemplate jdbcTemplate;

    public UsuarioDeletionAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteByIdCascade(Integer usuarioId) {
        LOGGER.info("UsuarioDeletionAdapter: starting cascade delete for usuario id={}", usuarioId);
        try {
            Integer fichaId = null;
            try {
                fichaId = jdbcTemplate.queryForObject("SELECT fk_ficha FROM usuario WHERE id_usuario = ?", Integer.class, usuarioId);
            } catch (DataAccessException e) {
                // may be null if user not found or fk_ficha is null
            }

            Integer voluntarioId = null;
            try {
                voluntarioId = jdbcTemplate.queryForObject("SELECT id_voluntario FROM voluntario WHERE fk_usuario = ?", Integer.class, usuarioId);
            } catch (DataAccessException e) {
                // might not exist
            }

            if (voluntarioId != null) {
                jdbcTemplate.update("DELETE FROM voluntario_especialidade WHERE fk_voluntario = ?", voluntarioId);
                jdbcTemplate.update("DELETE FROM disponibilidade_voluntario WHERE fk_voluntario = ?", voluntarioId);
                jdbcTemplate.update("DELETE FROM voluntario WHERE fk_usuario = ?", usuarioId);
                LOGGER.debug("Removed voluntario-related rows for usuario id={}", usuarioId);
            }

            // remove consultas where user is specialist or client
            jdbcTemplate.update("DELETE FROM consulta WHERE fk_especialista = ? OR fk_cliente = ?", usuarioId, usuarioId);

            if (fichaId != null) {
                jdbcTemplate.update("DELETE FROM telefone WHERE fk_ficha = ?", fichaId);
                LOGGER.debug("Removed telefones for ficha id={}", fichaId);
            }

            // optional tables
            try { jdbcTemplate.update("DELETE FROM oauth_token WHERE fk_usuario = ?", usuarioId); } catch (DataAccessException ignored) {}
            try { jdbcTemplate.update("DELETE FROM acesso_usuario WHERE fk_usuario = ?", usuarioId); } catch (DataAccessException ignored) {}

            if (fichaId != null) {
                jdbcTemplate.update("DELETE FROM ficha WHERE id_ficha = ?", fichaId);
                LOGGER.debug("Removed ficha id={}", fichaId);
            }

            jdbcTemplate.update("DELETE FROM usuario WHERE id_usuario = ?", usuarioId);
            LOGGER.info("UsuarioDeletionAdapter: cascade delete completed for usuario id={}", usuarioId);
        } catch (Exception e) {
            LOGGER.error("UsuarioDeletionAdapter: error during cascade delete for usuario id={}", usuarioId, e);
            throw e;
        }
    }
}
