package org.com.imaapi.application.dto.consulta.output;

import org.com.imaapi.domain.model.FeedbackConsulta;

import java.time.LocalDateTime;

public class FeedbackOutput {
    private Integer idFeedback;
    private Integer idConsulta;
    private String comentario;
    private LocalDateTime dtFeedback;

    public FeedbackOutput() {}

    public FeedbackOutput(Integer idFeedback, Integer idConsulta, String comentario, LocalDateTime dtFeedback) {
        this.idFeedback = idFeedback;
        this.idConsulta = idConsulta;
        this.comentario = comentario;
        this.dtFeedback = dtFeedback;
    }

    public static FeedbackOutput fromEntity(FeedbackConsulta feedback) {
        return new FeedbackOutput(
            feedback.getIdFeedback(),
            feedback.getConsulta().getIdConsulta(),
            feedback.getComentario(),
            feedback.getDtFeedback()
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
}