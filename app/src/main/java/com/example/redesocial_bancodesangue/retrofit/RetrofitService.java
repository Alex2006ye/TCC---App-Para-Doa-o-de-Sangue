package com.example.redesocial_bancodesangue.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;

    public RetrofitService(){
        iniciarRetrofit();
    }

    private void iniciarRetrofit(){
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.6:8080/").addConverterFactory(GsonConverterFactory.create(new Gson())).build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
