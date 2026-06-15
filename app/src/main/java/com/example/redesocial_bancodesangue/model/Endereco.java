package com.example.redesocial_bancodesangue.model;

import com.google.gson.annotations.SerializedName;

public class Endereco {
    @SerializedName("logradouro") public String logradouro;
    @SerializedName("bairro")     public String bairro;
    @SerializedName("localidade") public String cidade;
    @SerializedName("uf")         public String estado;
    @SerializedName("erro")       public Boolean erro;
}