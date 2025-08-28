package org.com.imaapi.domain.model.consulta.input;

import org.com.imaapi.domain.model.enums.ModalidadeConsulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;
import org.com.imaapi.domain.model.especialidade.Especialidade;
import org.com.imaapi.domain.model.usuario.Usuario;
import java.time.LocalDateTime;

public class ConsultaInput {
    private LocalDateTime horario;
    private StatusConsulta status;
    private ModalidadeConsulta modalidade;
    private String local;
    private String observacoes;
    private Integer idEspecialidade;
    private Especialidade especialidade;
    private Integer idAssistido;
    private Usuario assistido;
    private Integer idVoluntario;
    private Usuario voluntario;

    public ConsultaInput() {}

    public ConsultaInput(LocalDateTime horario, StatusConsulta status, ModalidadeConsulta modalidade, String local,
                         String observacoes, Integer idEspecialidade, Especialidade especialidade,
                         Integer idAssistido, Usuario assistido, Integer idVoluntario, Usuario voluntario) {
        this.horario = horario;
        this.status = status;
        this.modalidade = modalidade;
        this.local = local;
        this.observacoes = observacoes;
        this.idEspecialidade = idEspecialidade;
        this.especialidade = especialidade;
        this.idAssistido = idAssistido;
        this.assistido = assistido;
        this.idVoluntario = idVoluntario;
        this.voluntario = voluntario;
    }

    public void validate() {
        if (horario == null)
            throw new IllegalArgumentException("O horário da consulta é obrigatório");
        if (status == null)
            throw new IllegalArgumentException("Status não pode estar em branco");
        if (modalidade == null)
            throw new IllegalArgumentException("Indique como será a consulta, online ou presencial");
        if (local == null || local.trim().isEmpty())
            throw new IllegalArgumentException("O local da consulta não pode estar em branco");
    }

    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }

    public StatusConsulta getStatus() { return status; }
    public void setStatus(StatusConsulta status) { this.status = status; }

    public ModalidadeConsulta getModalidade() { return modalidade; }
    public void setModalidade(ModalidadeConsulta modalidade) { this.modalidade = modalidade; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Integer getIdEspecialidade() { return idEspecialidade; }
    public void setIdEspecialidade(Integer idEspecialidade) { this.idEspecialidade = idEspecialidade; }

    public Especialidade getEspecialidade() { return especialidade; }
    public void setEspecialidade(Especialidade especialidade) { this.especialidade = especialidade; }

    public Integer getIdAssistido() { return idAssistido; }
    public void setIdAssistido(Integer idAssistido) { this.idAssistido = idAssistido; }

    public Usuario getAssistido() { return assistido; }
    public void setAssistido(Usuario assistido) { this.assistido = assistido; }

    public Integer getIdVoluntario() { return idVoluntario; }
    public void setIdVoluntario(Integer idVoluntario) { this.idVoluntario = idVoluntario; }

    public Usuario getVoluntario() { return voluntario; }
    public void setVoluntario(Usuario voluntario) { this.voluntario = voluntario; }
}