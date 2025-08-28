package org.com.imaapi.domain.model.usuario.input;

import org.com.imaapi.domain.model.enums.Funcao;
import java.util.List;

public class VoluntarioDadosProfissionaisInput {
    private Funcao funcao;
    private String registroProfissional;
    private String biografiaProfissional;
    private String especialidade;
    private List<String> especialidades;

    public VoluntarioDadosProfissionaisInput() {}

    public VoluntarioDadosProfissionaisInput(Funcao funcao, String registroProfissional, String biografiaProfissional,
                                             String especialidade, List<String> especialidades) {
        this.funcao = funcao;
        this.registroProfissional = registroProfissional;
        this.biografiaProfissional = biografiaProfissional;
        this.especialidade = especialidade;
        this.especialidades = especialidades;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        this.registroProfissional = registroProfissional;
    }

    public String getBiografiaProfissional() {
        return biografiaProfissional;
    }

    public void setBiografiaProfissional(String biografiaProfissional) {
        this.biografiaProfissional = biografiaProfissional;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }
}