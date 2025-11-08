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
    private Integer idEspecialidade; // corresponde a fk_especialidade

    @NotNull(message = "Cliente é obrigatório")
    private Integer idCliente; // corresponde a fk_cliente

    @NotNull(message = "Especialista é obrigatório")
    private Integer idEspecialista; // corresponde a fk_especialista

    public ConsultaInput() {}

    public ConsultaInput(LocalDateTime horario, StatusConsulta status, ModalidadeConsulta modalidade,
                         String local, String observacoes, Integer idEspecialidade, Integer idCliente, Integer idEspecialista, Integer idVoluntario) {
        this.horario = horario;
        this.status = status;
        this.modalidade = modalidade;
        this.local = local;
        this.observacoes = observacoes;
        this.idEspecialidade = idEspecialidade;
        this.idCliente = idCliente;
        this.idEspecialista = idEspecialista;
    }

    // ===== GETTERS E SETTERS =====

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

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdEspecialista() {
        return idEspecialista;
    }

    public void setIdEspecialista(Integer idEspecialista) {
        this.idEspecialista = idEspecialista;
    }

    public Integer getIdVoluntario() {
        this.IdVoluntario= idVoluntario;
    }
}
