package com.example.redesocial_bancodesangue.dto;

public class AgendamentoCreateDTO {
    private Integer idAgendamento;
    private String data;
    private Integer idUsuarioHemocentro;
    private Integer idUsuarioDoador;
    private Integer idCampanha;

    public AgendamentoCreateDTO() {
    }

    public AgendamentoCreateDTO(Integer idAgendamento, String data, Integer idUsuarioHemocentro, Integer idUsuarioDoador, Integer idCampanha) {
        this.idAgendamento = idAgendamento;
        this.data = data;
        this.idUsuarioHemocentro = idUsuarioHemocentro;
        this.idUsuarioDoador = idUsuarioDoador;
        this.idCampanha = idCampanha;
    }

    public Integer getIdCampanha() {
        return idCampanha;
    }

    public void setIdCampanha(Integer idCampanha) {
        this.idCampanha = idCampanha;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(Integer idAgendamento) {
        this.idAgendamento = idAgendamento;
    }
}
