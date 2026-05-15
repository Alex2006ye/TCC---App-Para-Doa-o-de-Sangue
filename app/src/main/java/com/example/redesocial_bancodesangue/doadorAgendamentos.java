package com.example.redesocial_bancodesangue;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.redesocial_bancodesangue.adapter.AgendamentoAdapter;
import com.example.redesocial_bancodesangue.adapter.CampanhasAdapter;
import com.example.redesocial_bancodesangue.adapter.RecyclerViewInterface;
import com.example.redesocial_bancodesangue.model.Agendamento;
import com.example.redesocial_bancodesangue.model.Campanha;
import com.example.redesocial_bancodesangue.retrofit.AgendamentoApi;
import com.example.redesocial_bancodesangue.retrofit.CampanhaApi;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link doadorAgendamentos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class doadorAgendamentos extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RetrofitService retrofit;
    private AgendamentoApi api;

    private RecyclerView recyclerView;
    private AgendamentoAdapter adapter;
    private List<Agendamento> lista;
    private Integer idDoador = -1;

    public doadorAgendamentos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment doadorAgendamentos.
     */
    // TODO: Rename and change types and number of parameters
    public static doadorAgendamentos newInstance(int idDoador) {
        doadorAgendamentos fragment = new doadorAgendamentos();
        Bundle args = new Bundle();
        args.putInt("idDoador", idDoador);
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

        retrofit = new RetrofitService();
        api = retrofit.getRetrofit().create(AgendamentoApi.class);

        if (getArguments() != null) {
            idDoador = getArguments().getInt("idDoador");
        } else {
            idDoador = -1;
        }

        lista = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_doador_agendamentos, container, false);

        recyclerView = view.findViewById(R.id.recyclerAgendamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AgendamentoAdapter(lista, this);

        recyclerView.setAdapter(adapter);

        buscarAgendamentosDoUsuario();

        return inflater.inflate(R.layout.fragment_doador_agendamentos, container, false);
    }

    private void buscarAgendamentosDoUsuario(){
        api.listarAgendamentosValidosUsuarioDoador(idDoador).enqueue(new Callback<List<Agendamento>>() {
            @Override
            public void onResponse(Call<List<Agendamento>> call, Response<List<Agendamento>> response) {
                if(response.isSuccessful() && response.body() != null){
                    lista.clear();
                    lista.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "A lista não pode ser carregada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Agendamento>> call, Throwable throwable) {
                Toast.makeText(getActivity(), "A lista não carregou os dados ", Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Agendamento agendamento = lista.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Deseja deletar o agendamento desse Id? " + agendamento.getIdAgendamento()); //Titulo da caixinha com nome do Ingressante
        builder.setItems(new CharSequence[]{"Deletar Agendamento"},
                (dialog, which) -> {
                    if (which == 0) {
                        api.deletarAgendamento(agendamento.getIdAgendamento()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    lista.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    Toast.makeText(getContext(), "Agendamento Deletado Com Sucesso", Toast.LENGTH_SHORT).show();
                                } else{
                                    Toast.makeText(getContext(), "Erro ao excluir agendamento", Toast.LENGTH_SHORT).show();
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