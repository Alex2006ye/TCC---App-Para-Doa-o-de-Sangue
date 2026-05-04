package com.example.redesocial_bancodesangue.model;

import java.util.Date;

public class Campanha {
    private Integer idCampanha;
    private Integer idUsuarioHemocentro;
    private String nomeCampanha;
    private String dataInicio;
    private String dataFim;
    private TipoSanguineo tipoSanguineoVisado;

    public Campanha(Integer idCampanha, Integer idUsuarioHemocentro, String nomeCampanha, String dataInicio, String dataFim, TipoSanguineo tipoSanguineoVisado) {
        this.idCampanha = idCampanha;
        this.idUsuarioHemocentro = idUsuarioHemocentro;
        this.nomeCampanha = nomeCampanha;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipoSanguineoVisado = tipoSanguineoVisado;
    }

    public Campanha(){

    }

    public Integer getIdCampanha() {
        return idCampanha;
    }

    public void setIdCampanha(Integer idCampanha) {
        this.idCampanha = idCampanha;
    }

    public Integer getIdUsuarioHemocentro() {
        return idUsuarioHemocentro;
    }

    public void setIdUsuarioHemocentro(Integer idUsuarioHemocentro) {
        this.idUsuarioHemocentro = idUsuarioHemocentro;
    }

    public String getNomeCampanha() {
        return nomeCampanha;
    }

    public void setNomeCampanha(String nomeCampanha) {
        this.nomeCampanha = nomeCampanha;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public TipoSanguineo getTipoSanguineoVisado() {
        return tipoSanguineoVisado;
    }

    public void setTipoSanguineoVisado(TipoSanguineo tipoSanguineoVisado) {
        this.tipoSanguineoVisado = tipoSanguineoVisado;
    }
}
