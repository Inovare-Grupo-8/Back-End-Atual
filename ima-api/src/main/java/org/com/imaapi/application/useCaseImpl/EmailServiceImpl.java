package org.com.imaapi.application.useCaseImpl;

public interface EmailServiceImpl {
    public String enviarEmail(String destinatario, String nome, String assunto);
}