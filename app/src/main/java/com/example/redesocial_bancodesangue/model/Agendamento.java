package com.example.redesocial_bancodesangue.model;

import java.util.Date;

public class Agendamento {
    private Integer idAgendamento;
    private Integer idUsuarioDoador;
    private Integer idUsuarioHemocentro;
    private Date dataHora;

    public Agendamento(Integer idAgendamento, Integer idUsuarioDoador, Integer idUsuarioHemocentro, Date dataHora) {
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

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
}
