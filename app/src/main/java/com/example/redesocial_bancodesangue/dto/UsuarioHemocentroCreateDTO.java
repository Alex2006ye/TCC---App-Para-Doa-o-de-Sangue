package com.example.redesocial_bancodesangue.dto;

import com.example.redesocial_bancodesangue.model.TipoUsuario;

public class UsuarioHemocentroCreateDTO {
    private String nome, email, senha, cnpj, rua, bairro, cep;
    private Integer numero;
    private TipoUsuario tipoUsuario;

    public UsuarioHemocentroCreateDTO(String nome, String email, String senha, String cnpj, TipoUsuario tipoUsuario, String rua, String bairro, Integer numero, String cep) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cnpj = cnpj;
        this.tipoUsuario = tipoUsuario;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.cep = cep;
    }

    public UsuarioHemocentroCreateDTO(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCep(){return cep;}
    public void setCep(String cep){this.cep = cep;}
}
