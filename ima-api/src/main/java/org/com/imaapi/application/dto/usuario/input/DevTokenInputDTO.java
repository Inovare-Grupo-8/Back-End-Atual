package org.com.imaapi.application.dto.usuario.input;

import java.util.List;

public class DevTokenInputDTO {
    private String email;
    private String nome;
    private List<String> authorities;
    private Long validityInSeconds;

    public DevTokenInputDTO() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public Long getValidityInSeconds() {
        return validityInSeconds;
    }

    public void setValidityInSeconds(Long validityInSeconds) {
        this.validityInSeconds = validityInSeconds;
    }
}