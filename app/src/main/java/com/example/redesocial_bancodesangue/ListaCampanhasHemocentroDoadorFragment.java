package com.example.redesocial_bancodesangue;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial_bancodesangue.adapter.CampanhasAdapter;
import com.example.redesocial_bancodesangue.adapter.RecyclerViewInterface;
import com.example.redesocial_bancodesangue.dto.AgendamentoCreateDTO;
import com.example.redesocial_bancodesangue.model.Campanha;
import com.example.redesocial_bancodesangue.retrofit.AgendamentoApi;
import com.example.redesocial_bancodesangue.retrofit.CampanhaApi;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaCampanhasHemocentroDoadorFragment extends Fragment implements RecyclerViewInterface {

    private Integer idHemocentro, idDoador;

    private RecyclerView recyclerCampanhas;

    private CampanhasAdapter adapter;

    private List<Campanha> campanhas = new ArrayList<>();

    public ListaCampanhasHemocentroDoadorFragment() {
    }

    public static ListaCampanhasHemocentroDoadorFragment newInstance(int idHemocentro, int idDoador) {

        ListaCampanhasHemocentroDoadorFragment fragment = new ListaCampanhasHemocentroDoadorFragment();

        Bundle args = new Bundle();

        args.putInt("idHemocentro", idHemocentro);
        args.putInt("idDoador", idDoador);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            idHemocentro = getArguments().getInt("idHemocentro");
            idDoador = getArguments().getInt("idDoador");
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position) {

        Campanha campanha = campanhas.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        builder.setTitle(campanha.getNomeCampanha());

        builder.setMessage("Deseja participar desta campanha?");

        builder.setPositiveButton("Sim", (dialog, which) -> abrirSeletorData(campanha));

        builder.setNegativeButton("Não", null);

        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void criarAgendamento(Campanha campanha, LocalDateTime dataHoraAgendamento){
        RetrofitService retrofitService = new RetrofitService();

        AgendamentoApi api = retrofitService.getRetrofit().create(AgendamentoApi.class);

        AgendamentoCreateDTO dto = new AgendamentoCreateDTO();

        dto.setIdUsuarioDoador(idDoador);
        dto.setIdUsuarioHemocentro(idHemocentro);
        dto.setIdCampanha(campanha.getIdCampanha());
        dto.setData(dataHoraAgendamento.toString());

        api.criarAgendamento(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Agendamento realizado!", Toast.LENGTH_SHORT).show();
                } else if(response.code() == 409){
                    Toast.makeText(getContext(), "Você já participa desta campanha.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Erro ao criar agendamento", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void abrirSeletorData(Campanha campanha){

        LocalDate hoje = LocalDate.now();

        DatePickerDialog datePicker = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                LocalDate dataSelecionada = LocalDate.of(year, month + 1, dayOfMonth);
                    abrirSeletorHora(campanha, dataSelecionada);
                },
                hoje.getYear(), hoje.getMonthValue() - 1, hoje.getDayOfMonth()
        );

        datePicker.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void abrirSeletorHora(Campanha campanha, LocalDate dataSelecionada){
        TimePickerDialog timePicker = new TimePickerDialog(requireContext(),
                (view, hourOfDay, minute) -> {
                LocalDateTime dataHoraAgendamento = LocalDateTime.of(dataSelecionada, LocalTime.of(hourOfDay, minute));
                    criarAgendamento(campanha, dataHoraAgendamento);}, 8, 0, true);

        timePicker.show();
    }
}
