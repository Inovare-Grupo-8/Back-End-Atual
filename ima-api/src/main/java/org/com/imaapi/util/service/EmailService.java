package org.com.imaapi.util.service;

public interface EmailService {
    public String enviarEmail(String destinatario, String nome, String assunto);
}