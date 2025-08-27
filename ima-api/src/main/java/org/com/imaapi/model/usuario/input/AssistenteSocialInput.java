package org.com.imaapi.model.usuario.input;

import org.com.imaapi.model.enums.Funcao;
import org.com.imaapi.model.enums.TipoUsuario;

public class AssistenteSocialInput {
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String cpf;
    private String dataNascimento;
    private String genero;
    private TipoUsuario tipo = TipoUsuario.ADMINISTRADOR;
    private Funcao funcao = Funcao.ASSISTENCIA_SOCIAL;
    private String profissao;
    private String ddd;
    private String numero;
    private String cep;
    private String complemento;
    private String crp;
    private String especialidade;
    private String telefone;
    private String bio;

    public AssistenteSocialInput() {}

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCrp() {
        return crp;
    }

    public void setCrp(String crp) {
        this.crp = crp;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public static class UsuarioAutenticacaoInputValidator {
        public static void validar(UsuarioAutenticacaoInput input) {
            if (input.getEmail() == null || input.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email é obrigatório");
            }
            if (input.getSenha() == null || input.getSenha().trim().isEmpty()) {
                throw new IllegalArgumentException("Senha é obrigatória");
            }
        }
    }
}