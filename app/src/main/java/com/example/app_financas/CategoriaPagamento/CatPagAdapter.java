package com.example.app_financas.CategoriaPagamento;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_financas.R;

import java.util.List;
public class CatPagAdapter extends RecyclerView.Adapter<CatPagAdapter.ViewHolder> {
    private List<CatPag> catpags;
    private CatPagAdapter.OnItemClickListener listenerPag;

    public interface OnItemClickListener {
        void onItemClick(CatPag catpag);
    }

    public CatPagAdapter(List<CatPag> catpags, CatPagAdapter.OnItemClickListener listenerPag) {
        this.catpags = catpags;
        this.listenerPag = listenerPag;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeCategoriaPagamento;

        public ViewHolder(View itemView) {
            super(itemView);
            nomeCategoriaPagamento = itemView.findViewById(R.id.textViewNomeCatPag);
        }

        public void bind(CatPag catpag, CatPagAdapter.OnItemClickListener listenerPag) {
            nomeCategoriaPagamento.setText(catpag.getNome_categoriaPag());
            itemView.setOnClickListener(v -> listenerPag.onItemClick(catpag));
        }
    }
    @Override
    public CatPagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria_pagamento, parent, false);
        return new CatPagAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CatPagAdapter.ViewHolder holder, int position) {
        holder.bind(catpags.get(position), listenerPag);
    }
    @Override
    public int getItemCount() {
        return catpags.size();
    }



}