package org.com.imaapi.application.dto.consulta.output;

import org.com.imaapi.domain.model.AvaliacaoConsulta;

import java.time.LocalDateTime;

public class AvaliacaoOutput {
    private Integer idAvaliacao;
    private Integer idConsulta;
    private Integer nota;
    private LocalDateTime dtAvaliacao;
    private Integer idUsuarioAvaliador;
    private String nomeUsuarioAvaliador;

    public AvaliacaoOutput() {}

    public AvaliacaoOutput(Integer idAvaliacao, Integer idConsulta, Integer nota, LocalDateTime dtAvaliacao,
                           Integer idUsuarioAvaliador, String nomeUsuarioAvaliador) {
        this.idAvaliacao = idAvaliacao;
        this.idConsulta = idConsulta;
        this.nota = nota;
        this.dtAvaliacao = dtAvaliacao;
        this.idUsuarioAvaliador = idUsuarioAvaliador;
        this.nomeUsuarioAvaliador = nomeUsuarioAvaliador;
    }

    public static AvaliacaoOutput fromEntity(AvaliacaoConsulta avaliacao) {
        Integer avaliadorId = null;
        String avaliadorNome = null;
        if (avaliacao.getConsulta() != null) {
            if (avaliacao.getConsulta().getAssistido() != null) {
                avaliadorId = avaliacao.getConsulta().getAssistido().getIdUsuario();
                if (avaliacao.getConsulta().getAssistido().getFicha() != null) {
                    avaliadorNome = avaliacao.getConsulta().getAssistido().getFicha().getNome();
                }
            } else if (avaliacao.getConsulta().getVoluntario() != null) {
                avaliadorId = avaliacao.getConsulta().getVoluntario().getIdUsuario();
                if (avaliacao.getConsulta().getVoluntario().getFicha() != null) {
                    avaliadorNome = avaliacao.getConsulta().getVoluntario().getFicha().getNome();
                }
            }
        }
        return new AvaliacaoOutput(
            avaliacao.getIdAvaliacao(),
            avaliacao.getConsulta().getIdConsulta(),
            avaliacao.getNota(),
            avaliacao.getDtAvaliacao(),
            avaliadorId,
            avaliadorNome
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

    public Integer getIdUsuarioAvaliador() {
        return idUsuarioAvaliador;
    }

    public void setIdUsuarioAvaliador(Integer idUsuarioAvaliador) {
        this.idUsuarioAvaliador = idUsuarioAvaliador;
    }

    public String getNomeUsuarioAvaliador() {
        return nomeUsuarioAvaliador;
    }

    public void setNomeUsuarioAvaliador(String nomeUsuarioAvaliador) {
        this.nomeUsuarioAvaliador = nomeUsuarioAvaliador;
    }
}