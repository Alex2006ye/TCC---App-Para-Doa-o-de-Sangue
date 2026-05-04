package com.example.redesocial_bancodesangue.dto;

import com.example.redesocial_bancodesangue.model.TipoSanguineo;

public class CampanhaUpdateDTO {
    private Integer idCampanha;
    private String nomeCampanha, dataInicio, dataFim;
    private TipoSanguineo tipoSanguineoVisado;

    public CampanhaUpdateDTO(Integer idCampanha, String nomeCampanha, String dataInicio, String dataFim, TipoSanguineo tipoSanguineoVisado) {
        this.idCampanha = idCampanha;
        this.nomeCampanha = nomeCampanha;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipoSanguineoVisado = tipoSanguineoVisado;
    }

    public CampanhaUpdateDTO() {

    }

    public Integer getIdCampanha() {
        return idCampanha;
    }

    public void setIdCampanha(Integer idCampanha) {
        this.idCampanha = idCampanha;
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
