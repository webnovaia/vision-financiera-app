package com.vision.financiera.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vision.financiera.R;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String categoria);
    }

    private final List<CategoriaItem> categorias;
    private final OnItemClickListener listener;

    public CategoriaAdapter(List<CategoriaItem> categorias, OnItemClickListener listener) {
        this.categorias = categorias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriaItem item = categorias.get(position);
        holder.icon.setImageResource(item.icono);
        holder.nombre.setText(item.nombre);
        holder.icon.setOnClickListener(v -> listener.onItemClick(item.nombre));
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton icon;
        TextView nombre;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.btnCategoria);
            nombre = itemView.findViewById(R.id.tvNombreCategoria);
        }
    }
}
