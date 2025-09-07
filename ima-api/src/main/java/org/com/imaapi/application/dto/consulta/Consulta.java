package org.com.imaapi.application.dto.consulta;

import org.com.imaapi.domain.model.enums.ModalidadeConsulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.model.especialidade.Especialidade;
import org.com.imaapi.domain.model.Usuario;

import java.time.LocalDateTime;

public class Consulta {
    private Integer idConsulta;
    private LocalDateTime horario;
    private StatusConsulta status;
    private ModalidadeConsulta modalidade;
    private String local;
    private String observacoes;
    private Especialidade especialidade;
    private Usuario assistido;
    private Usuario voluntario;
    private String feedbackStatus = "PENDENTE";
    private String avaliacaoStatus = "PENDENTE";

    public Consulta() {}

    public Consulta(Integer idConsulta, LocalDateTime horario, StatusConsulta status, ModalidadeConsulta modalidade,
                    String local, String observacoes, Especialidade especialidade, Usuario assistido,
                    Usuario voluntario, String feedbackStatus, String avaliacaoStatus) {
        this.idConsulta = idConsulta;
        this.horario = horario;
        this.status = status;
        this.modalidade = modalidade;
        this.local = local;
        this.observacoes = observacoes;
        this.especialidade = especialidade;
        this.assistido = assistido;
        this.voluntario = voluntario;
        this.feedbackStatus = feedbackStatus;
        this.avaliacaoStatus = avaliacaoStatus;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    public ModalidadeConsulta getModalidade() {
        return modalidade;
    }

    public void setModalidade(ModalidadeConsulta modalidade) {
        this.modalidade = modalidade;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public Usuario getAssistido() {
        return assistido;
    }

    public void setAssistido(Usuario assistido) {
        this.assistido = assistido;
    }

    public Usuario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Usuario voluntario) {
        this.voluntario = voluntario;
    }

    public String getFeedbackStatus() {
        return feedbackStatus;
    }

    public void setFeedbackStatus(String feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    public String getAvaliacaoStatus() {
        return avaliacaoStatus;
    }

    public void setAvaliacaoStatus(String avaliacaoStatus) {
        this.avaliacaoStatus = avaliacaoStatus;
    }
}