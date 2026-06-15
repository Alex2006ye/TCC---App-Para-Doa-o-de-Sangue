package com.example.redesocial_bancodesangue.retrofit;

import com.example.redesocial_bancodesangue.dto.AgendamentoCreateDTO;
import com.example.redesocial_bancodesangue.model.Agendamento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AgendamentoApi {
    @GET("agendamento/agendamentosValidosDoador/{idDoador}")
    Call<List<Agendamento>> listarAgendamentosValidosUsuarioDoador(@Path("idDoador") Integer idDoador);

    @DELETE("agendamento/deletarAgendamento/{idAgendamento}")
    Call<Void> deletarAgendamento(@Path("idAgendamento") Integer idAgendamento);

    @GET("agendamento/agendamentosAtivosHemo/{idHemo}")
    Call<Integer> contarAgendamentosAtivosHemo(@Path("idHemo") Integer idHemo);

    @POST("agendamento/criarAgendamento")
    Call<Void> criarAgendamento(@Body AgendamentoCreateDTO dto);

    @GET("agendamento/participantesCampanhas/{idHemocentro}")
    Call<Integer> contarParticipantesCampanhas(@Path("idHemocentro") Integer idHemocentro);
}
