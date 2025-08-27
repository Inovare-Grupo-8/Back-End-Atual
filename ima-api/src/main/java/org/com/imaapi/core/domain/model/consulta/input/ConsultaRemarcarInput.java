package org.com.imaapi.model.consulta.input;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.com.imaapi.model.enums.ModalidadeConsulta;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultaRemarcarInput {
    private LocalDateTime novoHorario;
    private ModalidadeConsulta modalidade;
    private String local;
    private String observacoes;

    public ConsultaRemarcarInput() {}

    public ConsultaRemarcarInput(LocalDateTime novoHorario, ModalidadeConsulta modalidade, String local, String observacoes) {
        this.novoHorario = novoHorario;
        this.modalidade = modalidade;
        this.local = local;
        this.observacoes = observacoes;
    }

    public void validate() {
        if (novoHorario == null)
            throw new IllegalArgumentException("O novo horário da consulta é obrigatório");
        if (modalidade == null)
            throw new IllegalArgumentException("Indique como será a consulta, online ou presencial");
        if (local == null || local.trim().isEmpty())
            throw new IllegalArgumentException("O local da consulta não pode estar em branco");
    }

    public LocalDateTime getNovoHorario() { return novoHorario; }
    public void setNovoHorario(LocalDateTime novoHorario) { this.novoHorario = novoHorario; }

    public ModalidadeConsulta getModalidade() { return modalidade; }
    public void setModalidade(ModalidadeConsulta modalidade) { this.modalidade = modalidade; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}