package com.example.redesocial_bancodesangue.dto;

import com.example.redesocial_bancodesangue.model.TipoSanguineo;

import java.time.LocalDate;

public class CampanhaCreateDTO {
    private String nomeCampanha;
    private LocalDate dataInicio, dataFim;
    private TipoSanguineo tipoSanguineo;
    private Integer idUsuarioHemocentro;

    public CampanhaCreateDTO(String nomeCampanha, LocalDate dataInicio, LocalDate dataFim, TipoSanguineo tipoSanguineo, Integer idUsuarioHemocentro) {
        this.nomeCampanha = nomeCampanha;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipoSanguineo = tipoSanguineo;
        this.idUsuarioHemocentro = idUsuarioHemocentro;
    }

    public CampanhaCreateDTO(){

    }

    public String getNomeCampanha() {
        return nomeCampanha;
    }

    public void setNomeCampanha(String nomeCampanha) {
        this.nomeCampanha = nomeCampanha;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public Integer getIdUsuarioHemocentro() {
        return idUsuarioHemocentro;
    }

    public void setIdUsuarioHemocentro(Integer idUsuarioHemocentro) {
        this.idUsuarioHemocentro = idUsuarioHemocentro;
    }
}
