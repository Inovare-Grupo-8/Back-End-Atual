package org.com.imaapi.application.dto.usuario.input;

public class UsuarioAutenticacaoInputDTO {
    private String email;
    private String senha;

    public UsuarioAutenticacaoInputDTO() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}