package com.example.redesocial_bancodesangue.dto;

public class UpdateLocationDTO {
    private Integer idUsuario;
    private Double latitude;
    private Double longitude;

    public UpdateLocationDTO(){

    }

    public UpdateLocationDTO(Integer idUsuario, Double latitude, Double longitude){
        this.idUsuario = idUsuario;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
