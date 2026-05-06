package com.example.redesocial_bancodesangue;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.redesocial_bancodesangue.adapter.CampanhasAdapter;
import com.example.redesocial_bancodesangue.adapter.RecyclerViewInterface;
import com.example.redesocial_bancodesangue.model.Campanha;
import com.example.redesocial_bancodesangue.retrofit.CampanhaApi;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CampanhasHemocentroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CampanhasHemocentroFragment extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RetrofitService retrofit;
    private CampanhaApi api;

    private RecyclerView recyclerView;
    private CampanhasAdapter adapter;
    private List<Campanha> lista;
    private FloatingActionButton button;
    private Integer idHemocentro = -1;

    public CampanhasHemocentroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param.
     * @param.
     * @return A new instance of fragment CampanhasHemocentroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CampanhasHemocentroFragment newInstance(int idHemocentro) {
        CampanhasHemocentroFragment fragment = new CampanhasHemocentroFragment();
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

        retrofit = new RetrofitService();
        api = retrofit.getRetrofit().create(CampanhaApi.class);

        if (getArguments() != null) {
            idHemocentro = getArguments().getInt("idHemocentro");
        } else {
            idHemocentro = -1;
        }

        lista = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_campanhas_hemocentro, container, false);

        recyclerView = view.findViewById(R.id.recyclerCampanhas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CampanhasAdapter(lista, this);

        recyclerView.setAdapter(adapter);

        buscarTodasAsCampanhas();

        button = view.findViewById(R.id.btnCriarCampanha);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TelaCriarCampanha.class);
                intent.putExtra("idHemocentro", idHemocentro);
                startActivity(intent);
            }
        });

        return view;
    }

    private void buscarTodasAsCampanhas(){
        api.buscarTodos(idHemocentro).enqueue(new Callback<List<Campanha>>() {
            @Override
            public void onResponse(Call<List<Campanha>> call, Response<List<Campanha>> response) {
                if(response.isSuccessful() && response.body() != null){
                    lista.clear();
                    lista.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else{
                    Toast.makeText(getActivity(), "A lista não pode ser carregada", Toast.LENGTH_SHORT).show();
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Campanha>> call, Throwable throwable) {
                Toast.makeText(getActivity(), "A lista não carregou os dados ", Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Campanha campanha = lista.get(position);
        System.out.println(campanha.getIdCampanha());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Opções para " + campanha.getNomeCampanha()); //Titulo da caixinha com nome do Ingressante
        builder.setItems(new CharSequence[]{"Alterar Campanha", "Deletar Campanha"}, //Opções
                (dialog, which) -> {
                    if (which == 0) {
                        Intent intent = new Intent(getContext(), TelaAlterarCampanha.class);
                        intent.putExtra("nomeCampanha", campanha.getNomeCampanha());
                        intent.putExtra("idCampanha", campanha.getIdCampanha());
                        intent.putExtra("dataInicio", campanha.getDataInicio());
                        intent.putExtra("dataFim", campanha.getDataFim());
                        intent.putExtra("tipoSanguineo", campanha.getTipoSanguineoVisado().name());
                        startActivity(intent);
                    } else if (which == 1) {
                        api.deletarCampanha(campanha.getIdCampanha()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    lista.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    Toast.makeText(getContext(), "Campanha Deletada Com Sucesso", Toast.LENGTH_SHORT).show();
                                } else{
                                    Toast.makeText(getContext(), "Erro ao deletar campanha", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable throwable) {
                                Toast.makeText(getContext(), "Falha em Deletar " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
        builder.show(); //Serve para exibir a caixinha
    }
}