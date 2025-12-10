package org.com.imaapi.application.dto.consulta.input;

import jakarta.validation.constraints.NotBlank;

public class FeedbackInput {
    @NotBlank(message = "O comentário é obrigatório")
    private String comentario;

    public FeedbackInput() {}

    public FeedbackInput(String comentario) {
        this.comentario = comentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}