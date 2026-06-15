package com.example.redesocial_bancodesangue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

            txtNome = itemView.findViewById(R.id.txtNomeHemocentroAgendadoRecycler);

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

        if(agendamento.getCampanha() != null){
            holder.txtNome.setText(
                    agendamento.getCampanha().getNomeCampanha()
            );
        }else{
            holder.txtNome.setText("Campanha não encontrada");
        }

        if(agendamento.getDataHora() != null){
            holder.txtDiaHora.setText(
                    agendamento.getDataHora()
            );
        }else{
            holder.txtDiaHora.setText("Sem data");
        }

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
