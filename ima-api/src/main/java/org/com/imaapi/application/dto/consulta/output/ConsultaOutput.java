package org.com.imaapi.application.dto.consulta.output;

import org.com.imaapi.domain.model.especialidade.Especialidade;
import org.com.imaapi.domain.model.Usuario;

import java.time.LocalDateTime;

public class ConsultaOutput {
    private Integer idConsulta;
    private LocalDateTime horario;
    private String status;
    private String modalidade;
    private String local;
    private String observacoes;
    private Integer idEspecialidade;
    private String nomeEspecialidade;
    private Integer idVoluntario;
    private String nomeVoluntario;
    private Integer idAssistido;
    private String nomeAssistido;
    private Integer idEspecialista;
    private String nomeEspecialista;
    private Integer idCliente;
    private String nomeCliente;
    private String feedbackStatus;
    private String avaliacaoStatus;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private Especialidade especialidade;
    private Usuario assistido;
    private Usuario voluntario;

    public ConsultaOutput() {}

    public Integer getIdConsulta() { return idConsulta; }
    public void setIdConsulta(Integer idConsulta) { this.idConsulta = idConsulta; }

    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Integer getIdEspecialidade() { return idEspecialidade; }
    public void setIdEspecialidade(Integer idEspecialidade) { this.idEspecialidade = idEspecialidade; }

    public String getNomeEspecialidade() { return nomeEspecialidade; }
    public void setNomeEspecialidade(String nomeEspecialidade) { this.nomeEspecialidade = nomeEspecialidade; }

    public Integer getIdVoluntario() { return idVoluntario; }
    public void setIdVoluntario(Integer idVoluntario) { this.idVoluntario = idVoluntario; }

    public String getNomeVoluntario() { return nomeVoluntario; }
    public void setNomeVoluntario(String nomeVoluntario) { this.nomeVoluntario = nomeVoluntario; }

    public Integer getIdAssistido() { return idAssistido; }
    public void setIdAssistido(Integer idAssistido) { this.idAssistido = idAssistido; }

    public String getNomeAssistido() { return nomeAssistido; }
    public void setNomeAssistido(String nomeAssistido) { this.nomeAssistido = nomeAssistido; }

    public Integer getIdEspecialista() { return idEspecialista; }
    public void setIdEspecialista(Integer idEspecialista) { this.idEspecialista = idEspecialista; }

    public String getNomeEspecialista() { return nomeEspecialista; }
    public void setNomeEspecialista(String nomeEspecialista) { this.nomeEspecialista = nomeEspecialista; }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getFeedbackStatus() { return feedbackStatus; }
    public void setFeedbackStatus(String feedbackStatus) { this.feedbackStatus = feedbackStatus; }

    public String getAvaliacaoStatus() { return avaliacaoStatus; }
    public void setAvaliacaoStatus(String avaliacaoStatus) { this.avaliacaoStatus = avaliacaoStatus; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }


    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public Usuario getAssistido() {
        return assistido;
    }

    public Usuario getVoluntario() {
        return voluntario;
    }


    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public void setAssistido(Usuario assistido) {
        this.assistido = assistido;
    }

    public void setVoluntario(Usuario voluntario) {
        this.voluntario = voluntario;
    }
}