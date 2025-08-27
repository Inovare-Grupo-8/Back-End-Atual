package org.com.imaapi.model.usuario.input;

import org.com.imaapi.model.enums.Funcao;
import org.com.imaapi.model.enums.TipoUsuario;
import java.time.LocalDate;

public class UsuarioInputSegundaFase {
    private LocalDate dataNascimento;
    private Double rendaMinima;
    private Double rendaMaxima;
    private String genero;
    private TipoUsuario tipo;
    private EnderecoInput endereco;
    private TelefoneInput telefone;
    private Funcao funcao;
    private String areaOrientacao;
    private String comoSoube;
    private String profissao;

    public UsuarioInputSegundaFase() {}

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Double getRendaMinima() {
        return rendaMinima;
    }

    public void setRendaMinima(Double rendaMinima) {
        this.rendaMinima = rendaMinima;
    }

    public Double getRendaMaxima() {
        return rendaMaxima;
    }

    public void setRendaMaxima(Double rendaMaxima) {
        this.rendaMaxima = rendaMaxima;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public EnderecoInput getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoInput endereco) {
        this.endereco = endereco;
    }

    public TelefoneInput getTelefone() {
        return telefone;
    }

    public void setTelefone(TelefoneInput telefone) {
        this.telefone = telefone;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public String getAreaOrientacao() {
        return areaOrientacao;
    }

    public void setAreaOrientacao(String areaOrientacao) {
        this.areaOrientacao = areaOrientacao;
    }

    public String getComoSoube() {
        return comoSoube;
    }

    public void setComoSoube(String comoSoube) {
        this.comoSoube = comoSoube;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public UsuarioInputSegundaFase(LocalDate dataNascimento, Double rendaMinima, Double rendaMaxima, String genero,
                                   TipoUsuario tipo, EnderecoInput endereco, TelefoneInput telefone, Funcao funcao,
                                   String areaOrientacao, String comoSoube, String profissao) {
        this.dataNascimento = dataNascimento;
        this.rendaMinima = rendaMinima;
        this.rendaMaxima = rendaMaxima;
        this.genero = genero;
        this.tipo = tipo;
        this.endereco = endereco;
        this.telefone = telefone;
        this.funcao = funcao;
        this.areaOrientacao = areaOrientacao;
        this.comoSoube = comoSoube;
        this.profissao = profissao;


    }



}