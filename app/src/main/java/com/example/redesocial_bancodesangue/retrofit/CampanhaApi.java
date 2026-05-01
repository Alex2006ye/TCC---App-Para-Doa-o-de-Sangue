package com.example.redesocial_bancodesangue.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CampanhaApi {
    @GET("/campanha/campanhasAtivas/{idHemo}")
    Call<Integer> contarCampanhasAtivas(@Path("idHemo") Integer idHemo);

    @GET("/campanha/campanhasFinalizadas/{idHemo}")
    Call<Integer> contarCampanhasFinalizadas(@Path("idHemo") Integer idHemo);
}
