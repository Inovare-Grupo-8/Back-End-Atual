package org.com.imaapi.model.usuario.input;

public class UsuarioAutenticacaoInput {
    private String email;
    private String senha;

    public UsuarioAutenticacaoInput() {}

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