package com.example.redesocial_bancodesangue.retrofit;

import com.example.redesocial_bancodesangue.model.Endereco;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepApi {
    @GET("ws/{cep}/json/")
    Call<Endereco> buscarCep(@Path("cep") String cep);
}