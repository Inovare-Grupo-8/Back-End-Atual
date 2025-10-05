package org.com.imaapi.application.useCaseImpl.perfil;

import org.com.imaapi.application.useCase.perfil.AtualizarDadosProfissionaisUseCase;
import org.com.imaapi.application.dto.usuario.input.VoluntarioDadosProfissionaisInput;
import org.com.imaapi.domain.model.Voluntario;
import org.com.imaapi.domain.model.Especialidade;
import org.com.imaapi.domain.model.VoluntarioEspecialidade;
import org.com.imaapi.domain.repository.VoluntarioRepository;
import org.com.imaapi.domain.repository.EspecialidadeRepository;
import org.com.imaapi.domain.repository.VoluntarioEspecialidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AtualizarDadosProfissionaisUseCaseImpl implements AtualizarDadosProfissionaisUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarDadosProfissionaisUseCaseImpl.class);

    @Autowired
    private VoluntarioRepository voluntarioRepository;
    
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    
    @Autowired
    private VoluntarioEspecialidadeRepository voluntarioEspecialidadeRepository;

    @Override
    public boolean atualizarDadosProfissionais(Integer usuarioId, VoluntarioDadosProfissionaisInput dadosProfissionais) {
        LOGGER.info("Atualizando dados profissionais para o voluntário com ID de usuário: {}", usuarioId);
        LOGGER.debug("Dados recebidos: {}", dadosProfissionais);

        try {
            Voluntario voluntario = voluntarioRepository.findByUsuario_IdUsuario(usuarioId);
            if (voluntario == null) {
                LOGGER.warn("Voluntário não encontrado para o ID de usuário: {}", usuarioId);
                return false;
            }

            // Atualizar dados profissionais
            if (dadosProfissionais.getRegistroProfissional() != null) {
                LOGGER.debug("Atualizando registro profissional: {}", dadosProfissionais.getRegistroProfissional());
                voluntario.setRegistroProfissional(dadosProfissionais.getRegistroProfissional());
            }

            if (dadosProfissionais.getBiografiaProfissional() != null) {
                LOGGER.debug("Atualizando biografia profissional");
                voluntario.setBiografiaProfissional(dadosProfissionais.getBiografiaProfissional());
            }

            // Atualizar função apenas se for fornecida
            if (dadosProfissionais.getFuncao() != null) {
                LOGGER.debug("Atualizando função: {}", dadosProfissionais.getFuncao());
                try {
                    voluntario.setFuncao(dadosProfissionais.getFuncao());
                } catch (Exception e) {
                    LOGGER.error("Erro ao atualizar função: {}", e.getMessage());
                }
            }

            // Atualizar especialidades
            if (dadosProfissionais.getEspecialidade() != null) {
                LOGGER.debug("Atualizando especialidade principal: {}", dadosProfissionais.getEspecialidade());
                atualizarEspecialidadesVoluntario(
                        voluntario,
                        dadosProfissionais.getEspecialidade(),
                        dadosProfissionais.getEspecialidades()
                );
            }

            voluntarioRepository.save(voluntario);
            LOGGER.info("Dados profissionais atualizados com sucesso para o voluntário com ID de usuário: {}", usuarioId);
            return true;
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar dados profissionais: {}", e.getMessage(), e);
            return false;
        }
    }

    private void atualizarEspecialidadesVoluntario(Voluntario voluntario, String especialidadePrincipal, List<String> especialidadesAdicionais) {
        // Buscar ou criar a especialidade principal
        Especialidade especialidadePrincipalEntity = especialidadeRepository.findByNome(especialidadePrincipal)
                .orElseGet(() -> {
                    Especialidade novaEspecialidade = new Especialidade();
                    novaEspecialidade.setNome(especialidadePrincipal);
                    return especialidadeRepository.save(novaEspecialidade);
                });

        // Remover todas as especialidades antigas
        voluntarioEspecialidadeRepository.deleteByVoluntario(voluntario);

        // Adicionar a especialidade principal
        VoluntarioEspecialidade principalVE = new VoluntarioEspecialidade();
        principalVE.setVoluntario(voluntario);
        principalVE.setEspecialidade(especialidadePrincipalEntity);
        principalVE.setPrincipal(true);
        voluntarioEspecialidadeRepository.save(principalVE);

        // Adicionar especialidades adicionais
        if (especialidadesAdicionais != null && !especialidadesAdicionais.isEmpty()) {
            for (String nomeEspecialidade : especialidadesAdicionais) {
                if (!nomeEspecialidade.equals(especialidadePrincipal)) {
                    Especialidade especialidade = especialidadeRepository.findByNome(nomeEspecialidade)
                            .orElseGet(() -> {
                                Especialidade novaEspecialidade = new Especialidade();
                                novaEspecialidade.setNome(nomeEspecialidade);
                                return especialidadeRepository.save(novaEspecialidade);
                            });

                    VoluntarioEspecialidade ve = new VoluntarioEspecialidade();
                    ve.setVoluntario(voluntario);
                    ve.setEspecialidade(especialidade);
                    ve.setPrincipal(false);
                    voluntarioEspecialidadeRepository.save(ve);
                }
            }
        }
    }
}