package com.example.redesocial_bancodesangue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial_bancodesangue.R;
import com.example.redesocial_bancodesangue.model.Agendamento;
import com.example.redesocial_bancodesangue.model.Usuario;
import com.example.redesocial_bancodesangue.retrofit.RetrofitService;
import com.example.redesocial_bancodesangue.retrofit.UsuarioApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendamentoAdapter extends RecyclerView.Adapter<AgendamentoAdapter.ViewHolder> {
    private List<Agendamento> lista;
    private static RecyclerViewInterface listener;

    public AgendamentoAdapter(List<Agendamento> lista, RecyclerViewInterface listener) {
        this.lista = lista;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView txtNome;
        TextView txtDiaHora;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            txtNome = itemView.findViewById(R.id.txtNomeCampanhaRecycler);
            txtDiaHora = itemView.findViewById(R.id.txtDiaHoraRecycler);
        }
    }

    @NonNull
    @Override
    public AgendamentoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agendamento, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Agendamento agendamento = lista.get(position);

        holder.txtDiaHora.setText(agendamento.getDataHora().toString());

        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi api = retrofitService.getRetrofit().create(UsuarioApi.class);

        api.buscarUsuarioPorId(agendamento.getIdUsuarioHemocentro()).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                holder.txtNome.setText(response.body().getNome());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable throwable) {
                holder.txtNome.setText("Nome indisponível");
            }
        });

        CardView card = holder.itemView.findViewById(R.id.cardAgendamento);

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
