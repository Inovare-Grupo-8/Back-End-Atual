package org.com.imaapi.application.Usecase.Consulta.dto;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.com.imaapi.domain.model.enums.ModalidadeConsulta;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultaRemarcarRequestDTO {

    @NotNull(message = "O novo horário da consulta é obrigatório")
    @Future(message = "O novo horário deve ser uma data e hora futuras")
    private LocalDateTime novoHorario;

    @NotNull(message = "Indique como será a nova consulta, online ou presencial")
    private ModalidadeConsulta modalidade;

    @NotBlank(message = "O local da nova consulta não pode estar em branco")
    private String local;

    private String observacoes;


    public ConsultaRemarcarRequestDTO(LocalDateTime novoHorario, ModalidadeConsulta modalidade,
                                      String local, String observacoes) {
        this.novoHorario = novoHorario;
        this.modalidade = modalidade;
        this.local = local;
        this.observacoes = observacoes;
    }



    public LocalDateTime getNovoHorario() {
        return novoHorario;
    }

    public void setNovoHorario(LocalDateTime novoHorario) {
        this.novoHorario = novoHorario;
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
}
