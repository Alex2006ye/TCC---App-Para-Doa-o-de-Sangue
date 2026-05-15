package com.example.redesocial_bancodesangue.retrofit;

import com.example.redesocial_bancodesangue.dto.LoginDTO;
import com.example.redesocial_bancodesangue.dto.UsuarioDoadorCreateDTO;
import com.example.redesocial_bancodesangue.dto.UsuarioHemocentroCreateDTO;
import com.example.redesocial_bancodesangue.model.Usuario;

import java.util.Optional;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioApi {
    @POST("users/login")
    Call<Usuario> consultar(@Body LoginDTO dto);

    @POST("users/salvarUsuarioDoador")
    Call<Void> salvarUsuarioDoador(@Body UsuarioDoadorCreateDTO dto);

    @POST("users/salvarUsuarioHemocentro")
    Call<Void> salvarUsuarioHemocentro(@Body UsuarioHemocentroCreateDTO dto);

    @GET("users/buscarUsuarioPorId/{id}")
    Call<Usuario> buscarUsuarioPorId(@Path("id") Integer id);
}
