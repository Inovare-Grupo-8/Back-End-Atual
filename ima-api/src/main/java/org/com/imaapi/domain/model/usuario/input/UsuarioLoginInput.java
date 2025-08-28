package org.com.imaapi.domain.model.usuario.input;

public class UsuarioLoginInput {
    private String email;
    private String senha;

    public UsuarioLoginInput() {}

    public UsuarioLoginInput(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

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