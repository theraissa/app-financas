package com.example.app_financas;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_financas.CategoriaGeral.CatGeral;
import com.example.app_financas.CategoriaGeral.CatGeralAdapter;

import java.util.List;
public class CatPagAdapter extends RecyclerView.Adapter<com.example.app_financas.CategoriaPagamento.CatPagAdapter.ViewHolder> {
    private List<CatGeral> catpags;
    private CatPagAdapter.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(CatPag catpag);
    }
    public CatPagAdapter(List<CatGeral> catpags, CatPagAdapter.OnItemClickListener listener) {
        this.catpags = catpags;
        this.listener = listener;
    }