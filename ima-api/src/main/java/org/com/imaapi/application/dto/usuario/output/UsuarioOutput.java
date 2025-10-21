package org.com.imaapi.application.dto.usuario.output;

import org.com.imaapi.domain.model.enums.Funcao;
import org.com.imaapi.domain.model.enums.TipoUsuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioOutput {
    // Dados básicos do usuário (primeira fase)
    private Integer id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String email;
    private LocalDateTime dataCadastro;
    
    private LocalDate dataNascimento;
    private Double rendaMinima;
    private Double rendaMaxima;
    private String genero;
    private TipoUsuario tipo;
    private EnderecoOutput endereco;
    private TelefoneOutput telefone;
    private Funcao funcao;
    private String areaOrientacao;
    private String comoSoube;
    private String profissao;

    public UsuarioOutput() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

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

    public EnderecoOutput getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoOutput endereco) {
        this.endereco = endereco;
    }

    public TelefoneOutput getTelefone() {
        return telefone;
    }

    public void setTelefone(TelefoneOutput telefone) {
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
}