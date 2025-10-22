package org.com.imaapi.application.dto.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailQueueMessage implements Serializable {
    private String destinatario;
    private String nome;
    private String assunto;
    private String emailVoluntario;
    private String senha;
    private Integer idUsuario;
    
    // Construtor para facilitar a criação a partir do EmailDto
    public EmailQueueMessage(EmailDto emailDto) {
        this.destinatario = emailDto.getDestinatario();
        this.nome = emailDto.getNome();
        this.assunto = emailDto.getAssunto();
        this.emailVoluntario = emailDto.getEmailVoluntario();
        this.senha = emailDto.getSenha();
        this.idUsuario = emailDto.getIdUsuario();
    }
    
    // Método para converter para EmailDto
    public EmailDto toEmailDto() {
        return new EmailDto(destinatario, nome, assunto, emailVoluntario, senha, idUsuario);
    }
}