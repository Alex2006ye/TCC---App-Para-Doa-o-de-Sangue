package com.example.redesocial_bancodesangue.retrofit;

import com.example.redesocial_bancodesangue.dto.LoginDTO;
import com.example.redesocial_bancodesangue.model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioApi {
    @POST("/users/login")
    Call<Usuario> consultar(@Body LoginDTO dto);
}
