package com.example.redesocial_bancodesangue.model;

import java.time.LocalDateTime;

public class Agendamento {
    private Integer idAgendamento;
    private Integer idUsuarioDoador;
    private Integer idUsuarioHemocentro;
    private LocalDateTime dataHora;

    public Agendamento(Integer idAgendamento, Integer idUsuarioDoador, Integer idUsuarioHemocentro, LocalDateTime dataHora) {
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
