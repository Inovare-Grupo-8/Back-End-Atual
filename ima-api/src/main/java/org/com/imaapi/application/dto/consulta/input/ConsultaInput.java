package org.com.imaapi.application.dto.consulta.input;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import org.com.imaapi.domain.model.enums.ModalidadeConsulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;

public class ConsultaInput {

    @NotNull(message = "O horário da consulta é obrigatório")
    @Future(message = "A consulta precisa ser agendada para uma data futura")
    private LocalDateTime horario;

    @NotNull(message = "Status não pode estar em branco")
    private StatusConsulta status;

    @NotNull(message = "Indique como será a consulta, online ou presencial")
    private ModalidadeConsulta modalidade;

    @NotBlank(message = "O local da consulta não pode estar em branco")
    private String local;

    private String observacoes;

    @NotNull(message = "Especialidade é obrigatória")
    private Integer idEspecialidade;

    @NotNull(message = "Assistido é obrigatório")
    private Integer idAssistido;

    @NotNull(message = "Voluntário é obrigatório")
    private Integer idVoluntario;

    @NotNull(message = "Especialista é obrigatório")
    private Integer idEspecialista; // corresponde a fk_especialista
    private Object IdVoluntario;

    public ConsultaInput() {}

    public ConsultaInput(LocalDateTime horario, StatusConsulta status, ModalidadeConsulta modalidade,
                         String local, String observacoes, Integer idEspecialidade, Integer idAssistido, Integer idVoluntario) {
        this.horario = horario;
        this.status = status;
        this.modalidade = modalidade;
        this.local = local;
        this.observacoes = observacoes;
        this.idEspecialidade = idEspecialidade;
        this.idAssistido = idAssistido;
        this.idVoluntario = idVoluntario;
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

    public Integer getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(Integer idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public Integer getIdAssistido() {
        return idAssistido;
    }

    public void setIdAssistido(Integer idAssistido) {
        this.idAssistido = idAssistido;
    }

    public Integer getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(Integer idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

    public Integer getIdEspecialista() {
        return idEspecialista;
    }

    public void setIdEspecialista(Integer idEspecialista) {
        this.idEspecialista = idEspecialista;
    }
}
