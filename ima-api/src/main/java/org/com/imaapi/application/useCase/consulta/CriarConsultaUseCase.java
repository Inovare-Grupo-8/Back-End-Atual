
package org.com.imaapi.application.useCase.consulta;

import org.com.imaapi.domain.repository.ConsultaRepository;
import org.com.imaapi.domain.repository.UsuarioRepository;
import org.com.imaapi.domain.repository.EspecialidadeRepository;
import org.com.imaapi.application.dto.consulta.input.ConsultaInput;
import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.application.mapper.ConsultaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CriarConsultaUseCase implements CriarConsultaUseCaseImpl {
    private static final Logger logger = LoggerFactory.getLogger(CriarConsultaUseCase.class);
    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public CriarConsultaUseCase(ConsultaRepository consultaRepository,
                                UsuarioRepository usuarioRepository,
                                EspecialidadeRepository especialidadeRepository) {
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
        this.especialidadeRepository = especialidadeRepository;
    }

    @Override
    public ConsultaOutput executar(ConsultaInput consultaInput) {
        try {
            logger.info("Recebendo input para criar consulta: {}", consultaInput);

            // Validação dos campos obrigatórios
            if (consultaInput == null || consultaInput.getIdEspecialidade() == null ||
                consultaInput.getIdAssistido() == null || consultaInput.getIdVoluntario() == null) {
                logger.error("Dados obrigatórios ausentes");
                throw new IllegalArgumentException("Dados obrigatórios ausentes");
            }

            // Criação do objeto Consulta
            Consulta consulta = new Consulta();
            consulta.setHorario(consultaInput.getHorario());
            consulta.setStatus(consultaInput.getStatus());
            consulta.setModalidade(consultaInput.getModalidade());
            consulta.setLocal(consultaInput.getLocal());
            consulta.setObservacoes(consultaInput.getObservacoes());

            // Buscar entidades relacionadas
            consulta.setEspecialidade(especialidadeRepository.findById(consultaInput.getIdEspecialidade())
                .orElseThrow(() -> new RuntimeException("Especialidade não encontrada")));
            consulta.setAssistido(usuarioRepository.findById(consultaInput.getIdAssistido())
                .orElseThrow(() -> new RuntimeException("Assistido não encontrado")));
            consulta.setVoluntario(usuarioRepository.findById(consultaInput.getIdVoluntario())
                .orElseThrow(() -> new RuntimeException("Voluntário não encontrado")));

            // Persistir consulta
            Consulta consultaSalva = consultaRepository.save(consulta);

            // Retornar DTO
            return ConsultaMapper.toOutput(consultaSalva);
        } catch (Exception e) {
            logger.error("Erro ao criar consulta: {}", e.getMessage());
            throw new RuntimeException("Erro ao criar consulta: " + e.getMessage());
        }
    }
}
