package com.example.redesocial_bancodesangue.retrofit;

import com.example.redesocial_bancodesangue.dto.CampanhaCreateDTO;
import com.example.redesocial_bancodesangue.dto.CampanhaUpdateDTO;
import com.example.redesocial_bancodesangue.model.Campanha;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CampanhaApi {
    @GET("campanha/campanhasAtivas/{idHemo}")
    Call<Integer> contarCampanhasAtivas(@Path("idHemo") Integer idHemo);

    @GET("campanha/campanhasFinalizadas/{idHemo}")
    Call<Integer> contarCampanhasFinalizadas(@Path("idHemo") Integer idHemo);

    @POST("campanha/criarCampanha")
    Call<Void> criarCampanha(@Body CampanhaCreateDTO dto);

    @DELETE("campanha/deletar/{idCampanha}")
    Call<Void> deletarCampanha(@Path("idCampanha") Integer idCampanha);

    @PUT("campanha/alterarCampanha")
    Call<Void> alterarCampanha(@Body CampanhaUpdateDTO dto);

    @GET("campanha/buscarTodos/{idUsuarioHemocentro}")
    Call<List<Campanha>> buscarTodos(@Path("idUsuarioHemocentro") Integer idUsuarioHemocentro);
}
