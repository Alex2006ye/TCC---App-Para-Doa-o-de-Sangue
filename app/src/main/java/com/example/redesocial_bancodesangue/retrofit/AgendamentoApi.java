package com.example.redesocial_bancodesangue.retrofit;

import com.example.redesocial_bancodesangue.model.Agendamento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AgendamentoApi {
    @GET("agendamento/agendamentosValidosDoador/{idDoador}")
    Call<List<Agendamento>> listarAgendamentosValidosUsuarioDoador(@Path("idDoador") Integer idDoador);

    @DELETE("agendamento/deletarAgendamento/{idAgendamento}")
    Call<Void> deletarAgendamento(@Path("idAgendamento") Integer idAgendamento);

    @GET("agendamento/agendamentosAtivosHemo/{idHemo}")
    Call<Integer> contarAgendamentosAtivosHemo(@Path("idHemo") Integer idHemo);
}
