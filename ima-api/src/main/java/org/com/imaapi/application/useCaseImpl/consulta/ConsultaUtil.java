package org.com.imaapi.application.useCaseImpl.consulta;

import org.com.imaapi.application.dto.consulta.output.ConsultaOutput;
import org.com.imaapi.application.dto.consulta.output.ConsultaSimpleOutput;
import org.com.imaapi.application.dto.usuario.output.UsuarioDetalhesOutput;
import org.com.imaapi.domain.model.Consulta;
import org.com.imaapi.domain.model.Especialidade;
import org.com.imaapi.domain.model.Ficha;
import org.com.imaapi.domain.model.Usuario;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.repository.ConsultaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsultaUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(ConsultaUtil.class);

    //Converte uma lista de Consulta para ConsultaOutput
    public List<ConsultaOutput> mapConsultasToOutput(List<Consulta> consultas) {
        return consultas.stream()
                .map(this::mapConsultaToOutput)
                .collect(Collectors.toList());
    }

    // Converte uma Consulta para ConsultaOutput
    public ConsultaOutput mapConsultaToOutput(Consulta consulta) {
        if (consulta == null) {
            logger.debug("Consulta nula recebida para conversão");
            return null;
        }
        
        try {
            ConsultaOutput output = new ConsultaOutput();
            output.setIdConsulta(consulta.getIdConsulta());
            output.setHorario(consulta.getHorario());
            output.setStatus(consulta.getStatus().toString());
            output.setModalidade(consulta.getModalidade().toString());
            output.setLocal(consulta.getLocal());
            output.setObservacoes(consulta.getObservacoes());
            
            if (consulta.getEspecialidade() != null) {
                output.setIdEspecialidade(consulta.getEspecialidade().getIdEspecialidade());
                output.setNomeEspecialidade(consulta.getEspecialidade().getNome());
                Especialidade esp = new Especialidade();
                esp.setIdEspecialidade(consulta.getEspecialidade().getIdEspecialidade());
                esp.setNome(consulta.getEspecialidade().getNome());
                output.setEspecialidade(esp);
            } else {
                output.setIdEspecialidade(0);
                output.setNomeEspecialidade("");
                output.setEspecialidade(null);
            }

            if (consulta.getVoluntario() != null) {
                output.setIdVoluntario(consulta.getVoluntario().getIdUsuario());
                output.setIdEspecialista(consulta.getVoluntario().getIdUsuario());
                if (consulta.getVoluntario().getFicha() != null) {
                    String nomeVol = consulta.getVoluntario().getFicha().getNome();
                    output.setNomeVoluntario(nomeVol != null ? nomeVol : "");
                    output.setNomeEspecialista(nomeVol != null ? nomeVol : "");
                } else {
                    output.setNomeVoluntario("");
                    output.setNomeEspecialista("");
                }
                Usuario vol = new Usuario();
                vol.setIdUsuario(consulta.getVoluntario().getIdUsuario());
                vol.setEmail(consulta.getVoluntario().getEmail());
                Ficha fichaVol = new Ficha();
                fichaVol.setNome(consulta.getVoluntario().getFicha() != null ? consulta.getVoluntario().getFicha().getNome() : "");
                fichaVol.setSobrenome(consulta.getVoluntario().getFicha() != null ? consulta.getVoluntario().getFicha().getSobrenome() : "");
                vol.setFicha(fichaVol);
                output.setVoluntario(vol);
            } else {
                output.setIdVoluntario(0);
                output.setNomeVoluntario("");
                output.setIdEspecialista(0);
                output.setNomeEspecialista("");
                output.setVoluntario(null);
            }

            if (consulta.getAssistido() != null) {
                output.setIdAssistido(consulta.getAssistido().getIdUsuario());
                output.setIdCliente(consulta.getAssistido().getIdUsuario());
                if (consulta.getAssistido().getFicha() != null) {
                    String nomeAss = consulta.getAssistido().getFicha().getNome();
                    output.setNomeAssistido(nomeAss != null ? nomeAss : "");
                    output.setNomeCliente(nomeAss != null ? nomeAss : "");
                } else {
                    output.setNomeAssistido("");
                    output.setNomeCliente("");
                }
                Usuario ass = new Usuario();
                ass.setIdUsuario(consulta.getAssistido().getIdUsuario());
                ass.setEmail(consulta.getAssistido().getEmail());
                Ficha fichaAss = new Ficha();
                fichaAss.setNome(consulta.getAssistido().getFicha() != null ? consulta.getAssistido().getFicha().getNome() : "");
                fichaAss.setSobrenome(consulta.getAssistido().getFicha() != null ? consulta.getAssistido().getFicha().getSobrenome() : "");
                ass.setFicha(fichaAss);
                output.setAssistido(ass);
            } else {
                output.setIdAssistido(0);
                output.setNomeAssistido("");
                output.setIdCliente(0);
                output.setNomeCliente("");
                output.setAssistido(null);
            }

            // Campos de auditoria
            output.setFeedbackStatus(consulta.getFeedbackStatus());
            output.setAvaliacaoStatus(consulta.getAvaliacaoStatus());
            output.setCriadoEm(consulta.getCriadoEm());
            output.setAtualizadoEm(consulta.getAtualizadoEm());
            
            logger.debug("Consulta convertida com sucesso - ID: {}", consulta.getIdConsulta());
            return output;
            
        } catch (Exception e) {
            logger.error("Erro ao converter consulta: {}", e.getMessage());
            throw new RuntimeException("Erro ao mapear consulta para output", e);
        }
    }

    // Novo método para converter para DTO simples (sem campos null)
    public ConsultaSimpleOutput mapConsultaToSimpleOutput(Consulta consulta) {
        if (consulta == null) {
            logger.debug("Consulta nula recebida para conversão simples");
            return null;
        }
        
        try {
            ConsultaSimpleOutput output = new ConsultaSimpleOutput();
            output.setIdConsulta(consulta.getIdConsulta());
            output.setHorario(consulta.getHorario());
            output.setStatus(consulta.getStatus() != null ? consulta.getStatus().toString() : "");
            output.setModalidade(consulta.getModalidade() != null ? consulta.getModalidade().toString() : "");
            output.setLocal(consulta.getLocal());
            output.setObservacoes(consulta.getObservacoes());
            output.setFeedbackStatus(consulta.getFeedbackStatus());
            output.setAvaliacaoStatus(consulta.getAvaliacaoStatus());
            output.setCriadoEm(consulta.getCriadoEm());
            output.setAtualizadoEm(consulta.getAtualizadoEm());

            if (consulta.getAssistido() != null) {
                output.setAssistidoId(consulta.getAssistido().getIdUsuario());
                if (consulta.getAssistido().getFicha() != null) {
                    output.setAssistidoNome(consulta.getAssistido().getFicha().getNome());
                }
                if (consulta.getAssistido().getEmail() != null) {
                    output.setAssistidoEmail(consulta.getAssistido().getEmail());
                }
            }

            if (consulta.getVoluntario() != null) {
                output.setVoluntarioId(consulta.getVoluntario().getIdUsuario());
                if (consulta.getVoluntario().getFicha() != null) {
                    output.setVoluntarioNome(consulta.getVoluntario().getFicha().getNome());
                }
                if (consulta.getVoluntario().getEmail() != null) {
                    output.setVoluntarioEmail(consulta.getVoluntario().getEmail());
                }
            }

            if (consulta.getEspecialidade() != null) {
                output.setEspecialidadeId(consulta.getEspecialidade().getIdEspecialidade());
                output.setEspecialidadeNome(consulta.getEspecialidade().getNome());
            }
            
            logger.debug("Consulta convertida para DTO simples com sucesso - ID: {}", consulta.getIdConsulta());
            return output;
            
        } catch (Exception e) {
            logger.error("Erro ao converter consulta para DTO simples: {}", e.getMessage());
            throw new RuntimeException("Erro ao mapear consulta para output simples", e);
        }
    }

    //Obtém o usuário logado no sistema
    public Usuario getUsuarioLogado() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof UsuarioDetalhesOutput) {
                UsuarioDetalhesOutput userDetails =
                        (UsuarioDetalhesOutput) authentication.getPrincipal();

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(userDetails.getIdUsuario());
                usuario.setEmail(userDetails.getEmail());
                usuario.setTipo(userDetails.getTipo());

                logger.debug("Usuário logado recuperado: ID={}, Email={}, Tipo={}",
                        usuario.getIdUsuario(), usuario.getEmail(), usuario.getTipo());

                return usuario;
            } else {
                logger.error("Usuário não autenticado - contexto de segurança inválido");
                throw new RuntimeException("Usuário não autenticado. Faça login antes de acessar este recurso.");
            }
        } catch (Exception e) {
            logger.error("Erro crítico ao recuperar usuário autenticado: {}", e.getMessage());
            throw new RuntimeException("Falha na autenticação do usuário", e);
        }
    }

    public List<ConsultaSimpleOutput> mapConsultasToSimpleOutput(List<Consulta> consultas) {
        return consultas.stream()
                .map(this::mapConsultaToSimpleOutput)
                .collect(Collectors.toList());
    }

    // Valida se o tipo de usuário é válido
    public void validarTipoUsuario(String user) {
        if (user == null || user.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de usuário é obrigatório");
        }
        
        String userNormalizado = user.trim().toLowerCase();
        if (!userNormalizado.equals("voluntario") && !userNormalizado.equals("assistido")) {
            throw new IllegalArgumentException(
                String.format("Tipo de usuário inválido: '%s'. Valores aceitos: 'voluntario' ou 'assistido'", user)
            );
        }
    }

    //Retorna lista de status que representam consultas finalizadas/históricas
    public List<StatusConsulta> getStatusFinalizados() {
        return Arrays.asList(
                StatusConsulta.REALIZADA,
                StatusConsulta.CANCELADA
        );
    }

    //Retorna lista de status que representam consultas pendentes/futuras
    public List<StatusConsulta> getStatusPendentes() {
        return Arrays.asList(
                StatusConsulta.AGENDADA,
                StatusConsulta.REAGENDADA
        );
    }

    // Centraliza a lógica de escolha entre voluntário e assistido
    public List<Consulta> buscarConsultasPorTipoUsuario(
            ConsultaRepository repository,
            Integer userId, 
            String tipoUsuario, 
            java.time.LocalDateTime inicio, 
            java.time.LocalDateTime fim) {
        
        validarTipoUsuario(tipoUsuario);
        
        if (tipoUsuario.trim().equalsIgnoreCase("voluntario")) {
            return repository.findByVoluntario_IdUsuarioAndHorarioBetween(userId, inicio, fim);
        } else {
            return repository.findByAssistido_IdUsuarioAndHorarioBetween(userId, inicio, fim);
        }
    }

    // Valida se o ID é válido (não nulo e positivo)
    public void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                String.format("ID deve ser um número positivo, recebido: %s", id)
            );
        }
    }

    // Valida se uma string não é nula ou vazia
    public void validarStringObrigatoria(String valor, String nomeCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(
                String.format("%s é obrigatório", nomeCampo)
            );
        }
    }
}
