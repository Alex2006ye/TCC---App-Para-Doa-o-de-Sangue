package com.example.redesocial_bancodesangue.adapter;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial_bancodesangue.R;
import com.example.redesocial_bancodesangue.model.Campanha;

import java.time.LocalDate;
import java.util.List;

public class CampanhasAdapter extends RecyclerView.Adapter<CampanhasAdapter.ViewHolder>{

    private List<Campanha> lista;
    private static RecyclerViewInterface listener;

    public CampanhasAdapter(List<Campanha> lista, RecyclerViewInterface listener) {
        this.lista = lista;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView txtNome;
        TextView txtTipo;
        TextView txtStatus;
        TextView txtIdCampanha;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            txtNome = itemView.findViewById(R.id.txtNomeCampanhaRecycler);
            txtTipo = itemView.findViewById(R.id.txtTipoSanguineoRecycler);
            txtStatus = itemView.findViewById(R.id.txtStatusRecycler);
            txtIdCampanha = itemView.findViewById(R.id.txtIdCampanhaRecycler);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_campanha, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Campanha campanha = lista.get(position);

        holder.txtNome.setText(campanha.getNomeCampanha());
        holder.txtTipo.setText(campanha.getTipoSanguineoVisado().getS());
        holder.txtIdCampanha.setText(campanha.getIdCampanha().toString());

        LocalDate hoje = null;

        CardView card = holder.itemView.findViewById(R.id.cardCampanha);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            hoje = LocalDate.now();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(LocalDate.parse(campanha.getDataFim()).isBefore(hoje)){ //Ou seja, se a data fim já foi, quer dizer que está inativa
                holder.txtStatus.setText("Inativa");
            } else {
                holder.txtStatus.setText("Ativa");
                // coloca a cor verde no card se a campanha ainda estiver ativa
                card.setCardBackgroundColor(Color.parseColor("#009900"));
            }
        }

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
