package org.com.imaapi.application.dto.consulta.output;

import java.time.LocalDateTime;

/**
 * DTO simples para retorno de consulta por ID
 * Contém apenas dados básicos da consulta, sem relacionamentos
 */
public class ConsultaSimpleOutput {
    private Integer idConsulta;
    private LocalDateTime horario;
    private String status;
    private String modalidade;
    private String local;
    private String observacoes;
    private String feedbackStatus;
    private String avaliacaoStatus;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private Integer assistidoId;
    private String assistidoNome;
    private String assistidoEmail;
    private Integer voluntarioId;
    private String voluntarioNome;
    private String voluntarioEmail;
    private Integer especialidadeId;
    private String especialidadeNome;

    // Construtor padrão
    public ConsultaSimpleOutput() {}

    // Construtor completo
    public ConsultaSimpleOutput(Integer idConsulta, LocalDateTime horario, String status, 
                               String modalidade, String local, String observacoes,
                               String feedbackStatus, String avaliacaoStatus,
                               LocalDateTime criadoEm, LocalDateTime atualizadoEm,
                               Integer assistidoId, String assistidoNome, String assistidoEmail,
                               Integer voluntarioId, String voluntarioNome, String voluntarioEmail,
                               Integer especialidadeId, String especialidadeNome) {
        this.idConsulta = idConsulta;
        this.horario = horario;
        this.status = status;
        this.modalidade = modalidade;
        this.local = local;
        this.observacoes = observacoes;
        this.feedbackStatus = feedbackStatus;
        this.avaliacaoStatus = avaliacaoStatus;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        this.assistidoId = assistidoId;
        this.assistidoNome = assistidoNome;
        this.assistidoEmail = assistidoEmail;
        this.voluntarioId = voluntarioId;
        this.voluntarioNome = voluntarioNome;
        this.voluntarioEmail = voluntarioEmail;
        this.especialidadeId = especialidadeId;
        this.especialidadeNome = especialidadeNome;
    }

    // Getters e Setters
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
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

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Integer getAssistidoId() {
        return assistidoId;
    }

    public void setAssistidoId(Integer assistidoId) {
        this.assistidoId = assistidoId;
    }

    public String getAssistidoNome() {
        return assistidoNome;
    }

    public void setAssistidoNome(String assistidoNome) {
        this.assistidoNome = assistidoNome;
    }

    public String getAssistidoEmail() {
        return assistidoEmail;
    }

    public void setAssistidoEmail(String assistidoEmail) {
        this.assistidoEmail = assistidoEmail;
    }

    public Integer getVoluntarioId() {
        return voluntarioId;
    }

    public void setVoluntarioId(Integer voluntarioId) {
        this.voluntarioId = voluntarioId;
    }

    public String getVoluntarioNome() {
        return voluntarioNome;
    }

    public void setVoluntarioNome(String voluntarioNome) {
        this.voluntarioNome = voluntarioNome;
    }

    public String getVoluntarioEmail() {
        return voluntarioEmail;
    }

    public void setVoluntarioEmail(String voluntarioEmail) {
        this.voluntarioEmail = voluntarioEmail;
    }

    public Integer getEspecialidadeId() {
        return especialidadeId;
    }

    public void setEspecialidadeId(Integer especialidadeId) {
        this.especialidadeId = especialidadeId;
    }

    public String getEspecialidadeNome() {
        return especialidadeNome;
    }

    public void setEspecialidadeNome(String especialidadeNome) {
        this.especialidadeNome = especialidadeNome;
    }
}