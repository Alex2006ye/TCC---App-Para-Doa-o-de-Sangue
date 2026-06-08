package com.example.redesocial_bancodesangue;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial_bancodesangue.adapter.CampanhasAdapter;
import com.example.redesocial_bancodesangue.adapter.RecyclerViewInterface;
import com.example.redesocial_bancodesangue.model.Campanha;
import com.example.redesocial_bancodesangue.retrofit.CampanhaApi;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaCampanhasHemocentroDoadorFragment extends Fragment implements RecyclerViewInterface {

    private Integer idHemocentro;

    private RecyclerView recyclerCampanhas;

    private CampanhasAdapter adapter;

    private List<Campanha> campanhas = new ArrayList<>();

    public ListaCampanhasHemocentroDoadorFragment() {
    }

    public static ListaCampanhasHemocentroDoadorFragment newInstance(int idHemocentro) {

        ListaCampanhasHemocentroDoadorFragment fragment = new ListaCampanhasHemocentroDoadorFragment();

        Bundle args = new Bundle();

        args.putInt("idHemocentro", idHemocentro);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            idHemocentro = getArguments().getInt("idHemocentro");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.fragment_campanhas_hemocentro,
                container,
                false
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerCampanhas = view.findViewById(R.id.recyclerCampanhas);

        recyclerCampanhas.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new CampanhasAdapter(campanhas, this);

        recyclerCampanhas.setAdapter(adapter);

        carregarCampanhas();
    }

    private void carregarCampanhas() {

        RetrofitService retrofitService = new RetrofitService();

        CampanhaApi api = retrofitService.getRetrofit().create(CampanhaApi.class);

        api.buscarTodos(idHemocentro).enqueue(new Callback<List<Campanha>>() {
            @Override
            public void onResponse(Call<List<Campanha>> call, Response<List<Campanha>> response) {
                if(response.isSuccessful() && response.body() != null){
                    campanhas.clear();
                    campanhas.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Campanha>> call, Throwable t) {
                Log.e("CAMPANHAS", "Erro ao carregar campanhas", t);
            }
        });
    }

    @Override
    public void onItemClick(int position) {

        Campanha campanha =
                campanhas.get(position);

        Log.d(
                "CAMPANHA",
                "Campanha clicada: "
                        + campanha.getNomeCampanha()
        );
    }
}