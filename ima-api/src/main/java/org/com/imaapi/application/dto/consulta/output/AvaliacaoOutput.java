package org.com.imaapi.application.dto.consulta.output;

import org.com.imaapi.domain.model.AvaliacaoConsulta;

import java.time.LocalDateTime;

public class AvaliacaoOutput {
    private Integer idAvaliacao;
    private Integer idConsulta;
    private Integer nota;
    private LocalDateTime dtAvaliacao;

    public AvaliacaoOutput() {}

    public AvaliacaoOutput(Integer idAvaliacao, Integer idConsulta, Integer nota, LocalDateTime dtAvaliacao) {
        this.idAvaliacao = idAvaliacao;
        this.idConsulta = idConsulta;
        this.nota = nota;
        this.dtAvaliacao = dtAvaliacao;
    }

    public static AvaliacaoOutput fromEntity(AvaliacaoConsulta avaliacao) {
        return new AvaliacaoOutput(
            avaliacao.getIdAvaliacao(),
            avaliacao.getConsulta().getIdConsulta(),
            avaliacao.getNota(),
            avaliacao.getDtAvaliacao()
        );
    }

    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public LocalDateTime getDtAvaliacao() {
        return dtAvaliacao;
    }

    public void setDtAvaliacao(LocalDateTime dtAvaliacao) {
        this.dtAvaliacao = dtAvaliacao;
    }
}