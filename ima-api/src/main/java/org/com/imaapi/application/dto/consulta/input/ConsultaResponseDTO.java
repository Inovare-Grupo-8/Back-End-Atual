package org.com.imaapi.application.dto.consulta.input;

import org.com.imaapi.domain.model.enums.ModalidadeConsulta;
import org.com.imaapi.domain.model.enums.StatusConsulta;

import java.time.LocalDateTime;

public class ConsultaResponseDTO {
    private Integer idConsulta;
    private LocalDateTime horario;
    private StatusConsulta status;
    private ModalidadeConsulta modalidade;
    private String local;
    private String observacoes;
    private Integer idEspecialidade;
    private Integer idAssistido;
    private Integer idVoluntario;

    public ConsultaResponseDTO() {
    }

    public ConsultaResponseDTO(Consulta consulta) {
        this.idConsulta = consulta.getIdConsulta();
        this.horario = consulta.getHorario();
        this.status = consulta.getStatus();
        this.modalidade = consulta.getModalidade();
        this.local = consulta.getLocal();
        this.observacoes = consulta.getObservacoes();
        this.idEspecialidade = consulta.getEspecialidade() != null ? consulta.getEspecialidade().getId() : null;
        this.idAssistido = consulta.getAssistido() != null ? consulta.getAssistido().getId() : null;
        this.idVoluntario = consulta.getVoluntario() != null ? consulta.getVoluntario().getId() : null;
    }


    public Integer getIdConsulta() {
        return idConsulta;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public ModalidadeConsulta getModalidade() {
        return modalidade;
    }

    public String getLocal() {
        return local;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Integer getIdEspecialidade() {
        return idEspecialidade;
    }

    public Integer getIdAssistido() {
        return idAssistido;
    }

    public Integer getIdVoluntario() {
        return idVoluntario;
    }


    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    public void setModalidade(ModalidadeConsulta modalidade) {
        this.modalidade = modalidade;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public void setIdEspecialidade(Integer idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public void setIdAssistido(Integer idAssistido) {
        this.idAssistido = idAssistido;
    }

    public void setIdVoluntario(Integer idVoluntario) {
        this.idVoluntario = idVoluntario;
    }
}
