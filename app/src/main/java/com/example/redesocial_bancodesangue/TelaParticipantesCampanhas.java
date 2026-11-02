package com.example.redesocial_bancodesangue;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial_bancodesangue.adapter.ParticipantesAdapter;
import com.example.redesocial_bancodesangue.model.ParticipanteCampanha;
import com.example.redesocial_bancodesangue.retrofit.AgendamentoApi;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaParticipantesCampanhas extends AppCompatActivity {

    private final List<ParticipanteCampanha> lista = new ArrayList<>();
    private ParticipantesAdapter adapter;
    private TextView txtListaVazia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes_campanhas);

        int idHemocentro = getIntent().getIntExtra("idHemocentro", -1);

        txtListaVazia = findViewById(R.id.txtListaVazia);
        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        RecyclerView recycler = findViewById(R.id.recyclerParticipantes);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParticipantesAdapter(lista);
        recycler.setAdapter(adapter);

        carregarParticipantes(idHemocentro);
    }

    private void carregarParticipantes(int idHemocentro) {
        AgendamentoApi api = new RetrofitService().getRetrofit().create(AgendamentoApi.class);

        api.buscarParticipantes(idHemocentro).enqueue(new Callback<List<ParticipanteCampanha>>() {
            @Override
            public void onResponse(Call<List<ParticipanteCampanha>> call,
                                   Response<List<ParticipanteCampanha>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    lista.clear();
                    lista.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    txtListaVazia.setVisibility(lista.isEmpty() ? View.VISIBLE : View.GONE);
                } else {
                    Toast.makeText(TelaParticipantesCampanhas.this,
                            "Não foi possível carregar os participantes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ParticipanteCampanha>> call, Throwable t) {
                Log.e("PARTICIPANTES", "Erro", t);
                Toast.makeText(TelaParticipantesCampanhas.this,
                        "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}