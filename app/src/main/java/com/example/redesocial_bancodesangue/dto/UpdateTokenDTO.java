package com.example.redesocial_bancodesangue.dto;

public class UpdateTokenDTO {
    private Integer idUsuario;
    private String token;

    public UpdateTokenDTO(){

    }

    public UpdateTokenDTO(Integer idUsuario, String token) {
        this.idUsuario = idUsuario;
        this.token = token;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
