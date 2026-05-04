package com.example.redesocial_bancodesangue;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.redesocial_bancodesangue.adapter.RecyclerViewInterface;
import com.example.redesocial_bancodesangue.model.Campanha;
import com.example.redesocial_bancodesangue.retrofit.CampanhaApi;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeHemocentroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeHemocentroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView txtNumeroCampanhasAtivas, txtNumeroCampanhasFinalizadas;
    private RetrofitService retrofit;
    private CampanhaApi api;

    public HomeHemocentroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param
     * @param
     * @return A new instance of fragment HomeHemocentroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeHemocentroFragment newInstance(int idHemocentro) {
        HomeHemocentroFragment fragment = new HomeHemocentroFragment();
        Bundle args = new Bundle();
        args.putInt("idHemocentro", idHemocentro);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Integer idHemocentro = -1;

        if (getArguments() != null) {
            idHemocentro = getArguments().getInt("idHemocentro");
        }
        View view = inflater.inflate(R.layout.fragment_home_hemocentro, container, false);
        txtNumeroCampanhasAtivas = view.findViewById(R.id.numeroCard);
        txtNumeroCampanhasFinalizadas = view.findViewById(R.id.numeroSegundoCard);

        analisarCampanhasAtivas(idHemocentro);
        analisarCampanhasFinalizadas(idHemocentro);

        return view;
    }

    private void analisarCampanhasAtivas(Integer idHemocentro){

        retrofit = new RetrofitService();
        api = retrofit.getRetrofit().create(CampanhaApi.class);

        api.contarCampanhasAtivas(idHemocentro).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null){
                    txtNumeroCampanhasAtivas.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                Toast.makeText(getContext(), "Erro em trazer o dado atual de campanhas ativas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void analisarCampanhasFinalizadas(Integer idHemocentro){
        retrofit = new RetrofitService();
        api = retrofit.getRetrofit().create(CampanhaApi.class);

        api.contarCampanhasFinalizadas(idHemocentro).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null){
                    txtNumeroCampanhasFinalizadas.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                Toast.makeText(getContext(), "Erro em trazer o dado atual de campanhas finalizadas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}