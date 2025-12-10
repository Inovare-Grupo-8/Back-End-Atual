package org.com.imaapi.application.dto.consulta.output;

import org.com.imaapi.domain.model.FeedbackConsulta;

import java.time.LocalDateTime;

public class FeedbackOutput {
    private Integer idFeedback;
    private Integer idConsulta;
    private String comentario;
    private LocalDateTime dtFeedback;
    private Integer idUsuarioAvaliador;
    private String nomeUsuarioAvaliador;

    public FeedbackOutput() {}

    public FeedbackOutput(Integer idFeedback, Integer idConsulta, String comentario, LocalDateTime dtFeedback,
                          Integer idUsuarioAvaliador, String nomeUsuarioAvaliador) {
        this.idFeedback = idFeedback;
        this.idConsulta = idConsulta;
        this.comentario = comentario;
        this.dtFeedback = dtFeedback;
        this.idUsuarioAvaliador = idUsuarioAvaliador;
        this.nomeUsuarioAvaliador = nomeUsuarioAvaliador;
    }

    public static FeedbackOutput fromEntity(FeedbackConsulta feedback) {
        Integer avaliadorId = null;
        String avaliadorNome = null;
        if (feedback.getConsulta() != null) {
            if (feedback.getConsulta().getAssistido() != null) {
                avaliadorId = feedback.getConsulta().getAssistido().getIdUsuario();
                if (feedback.getConsulta().getAssistido().getFicha() != null) {
                    avaliadorNome = feedback.getConsulta().getAssistido().getFicha().getNome();
                }
            } else if (feedback.getConsulta().getVoluntario() != null) {
                avaliadorId = feedback.getConsulta().getVoluntario().getIdUsuario();
                if (feedback.getConsulta().getVoluntario().getFicha() != null) {
                    avaliadorNome = feedback.getConsulta().getVoluntario().getFicha().getNome();
                }
            }
        }
        return new FeedbackOutput(
            feedback.getIdFeedback(),
            feedback.getConsulta().getIdConsulta(),
            feedback.getComentario(),
            feedback.getDtFeedback(),
            avaliadorId,
            avaliadorNome
        );
    }

    public Integer getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(Integer idFeedback) {
        this.idFeedback = idFeedback;
    }

    public Integer getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Integer idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDtFeedback() {
        return dtFeedback;
    }

    public void setDtFeedback(LocalDateTime dtFeedback) {
        this.dtFeedback = dtFeedback;
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