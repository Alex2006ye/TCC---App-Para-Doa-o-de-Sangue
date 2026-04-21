package com.example.redesocial_bancodesangue.model;

import java.util.Date;

public class Campanha {
    private Integer idCampanha;
    private Integer idUsuarioHemocentro;
    private String nomeCampanha;
    private Date dataInicio;
    private Date dataFim;
    private TipoSanguineo tipoSanguineoVisado;
    private Boolean status;

    public Campanha(Integer idCampanha, Integer idUsuarioHemocentro, String nomeCampanha, Date dataInicio, Date dataFim, TipoSanguineo tipoSanguineoVisado, Boolean status) {
        this.idCampanha = idCampanha;
        this.idUsuarioHemocentro = idUsuarioHemocentro;
        this.nomeCampanha = nomeCampanha;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipoSanguineoVisado = tipoSanguineoVisado;
        this.status = status;
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

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public TipoSanguineo getTipoSanguineoVisado() {
        return tipoSanguineoVisado;
    }

    public void setTipoSanguineoVisado(TipoSanguineo tipoSanguineoVisado) {
        this.tipoSanguineoVisado = tipoSanguineoVisado;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
