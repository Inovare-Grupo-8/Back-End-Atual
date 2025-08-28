package org.com.imaapi.domain.model.usuario.input;

import org.com.imaapi.domain.model.enums.Funcao;

public class VoluntarioInput {
    private Funcao funcao;
    private Integer fkUsuario;

    public VoluntarioInput() {}

    public VoluntarioInput(Funcao funcao, Integer fkUsuario) {
        this.funcao = funcao;
        this.fkUsuario = fkUsuario;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public Integer getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Integer fkUsuario) {
        this.fkUsuario = fkUsuario;
    }
}