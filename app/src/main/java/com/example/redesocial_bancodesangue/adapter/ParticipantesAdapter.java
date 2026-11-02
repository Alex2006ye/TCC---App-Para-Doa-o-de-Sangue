package com.example.redesocial_bancodesangue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redesocial_bancodesangue.R;
import com.example.redesocial_bancodesangue.model.ParticipanteCampanha;

import java.util.List;

public class ParticipantesAdapter extends RecyclerView.Adapter<ParticipantesAdapter.ViewHolder> {

    private final List<ParticipanteCampanha> lista;

    public ParticipantesAdapter(List<ParticipanteCampanha> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtInicial, txtNome, txtCampanha, txtData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInicial = itemView.findViewById(R.id.txtInicialDoador);
            txtNome = itemView.findViewById(R.id.txtNomeDoadorParticipante);
            txtCampanha = itemView.findViewById(R.id.txtCampanhaParticipante);
            txtData = itemView.findViewById(R.id.txtDataParticipante);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_participante, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParticipanteCampanha p = lista.get(position);

        String nome = p.getNomeDoador() != null ? p.getNomeDoador().trim() : "";
        holder.txtNome.setText(nome.isEmpty() ? "Doador" : nome);
        holder.txtInicial.setText(nome.isEmpty() ? "?" : nome.substring(0, 1).toUpperCase());
        holder.txtCampanha.setText(p.getNomeCampanha());
        holder.txtData.setText(formatarData(p.getDataHora()));
    }

    // "2026-11-05 08:00:00" -> "05/11/2026 às 08:00"
    private String formatarData(String dataHora) {
        if (dataHora == null || dataHora.length() < 16) return "";
        String[] partes = dataHora.substring(0, 10).split("-");
        if (partes.length != 3) return dataHora;
        return partes[2] + "/" + partes[1] + "/" + partes[0] + " às " + dataHora.substring(11, 16);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}