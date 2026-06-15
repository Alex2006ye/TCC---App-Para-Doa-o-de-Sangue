package com.example.redesocial_bancodesangue.model;

import java.time.LocalDateTime;

public class Agendamento {
    private Integer idAgendamento;
    private Integer idUsuarioDoador;
    private Integer idUsuarioHemocentro;
    private String dataHora;
    private Campanha campanha;

    public Agendamento(Integer idAgendamento, Integer idUsuarioDoador, Integer idUsuarioHemocentro, String dataHora) {
        this.idAgendamento = idAgendamento;
        this.idUsuarioDoador = idUsuarioDoador;
        this.idUsuarioHemocentro = idUsuarioHemocentro;
        this.dataHora = dataHora;
    }

    public Agendamento(){

    }

    public Integer getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(Integer idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public Integer getIdUsuarioDoador() {
        return idUsuarioDoador;
    }

    public void setIdUsuarioDoador(Integer idUsuarioDoador) {
        this.idUsuarioDoador = idUsuarioDoador;
    }

    public Integer getIdUsuarioHemocentro() {
        return idUsuarioHemocentro;
    }

    public void setIdUsuarioHemocentro(Integer idUsuarioHemocentro) {
        this.idUsuarioHemocentro = idUsuarioHemocentro;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public Campanha getCampanha() {return campanha;}

    public void setCampanha(Campanha campanha) {this.campanha = campanha;}
}

