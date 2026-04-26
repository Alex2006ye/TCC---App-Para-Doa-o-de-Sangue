package com.example.redesocial_bancodesangue.dto;

import com.example.redesocial_bancodesangue.model.TipoSanguineo;
import com.example.redesocial_bancodesangue.model.TipoUsuario;

import java.time.LocalDate;

public class UsuarioDoadorCreateDTO {
    private String nome, email, senha, cpf;
    private Double peso;
    private String dataNasc;
    private TipoUsuario tipoUsuario;
    private TipoSanguineo tipoSanguineo;

    public UsuarioDoadorCreateDTO(String nome, String email, String senha, String cpf, Double peso, String dataNasc, TipoUsuario tipoUsuario, TipoSanguineo tipoSanguineo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.peso = peso;
        this.dataNasc = dataNasc;
        this.tipoUsuario = tipoUsuario;
        this.tipoSanguineo = tipoSanguineo;
    }

    public UsuarioDoadorCreateDTO() {

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }
}
