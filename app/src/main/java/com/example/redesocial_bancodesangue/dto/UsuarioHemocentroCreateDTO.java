package com.example.redesocial_bancodesangue.dto;

import com.example.redesocial_bancodesangue.model.TipoUsuario;

public class UsuarioHemocentroCreateDTO {
    private String nome, email, senha, cnpj;
    private TipoUsuario tipoUsuario;

    public UsuarioHemocentroCreateDTO(String nome, String email, String senha, String cnpj, TipoUsuario tipoUsuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cnpj = cnpj;
        this.tipoUsuario = tipoUsuario;
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
}
