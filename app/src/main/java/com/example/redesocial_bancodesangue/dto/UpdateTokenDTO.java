package com.example.redesocial_bancodesangue.dto;

public class UpdateTokenDTO {
    private Integer idUsuario;
    private String tokenFcm;

    public UpdateTokenDTO(){

    }

    public UpdateTokenDTO(Integer idUsuario, String tokenFcm) {
        this.idUsuario = idUsuario;
        this.tokenFcm = tokenFcm;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTokenFcm() {
        return tokenFcm;
    }

    public void setTokenFcm(String tokenFcm) {
        this.tokenFcm = tokenFcm;
    }
}
