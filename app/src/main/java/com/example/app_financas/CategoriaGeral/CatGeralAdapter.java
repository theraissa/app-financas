package com.example.app_financas.CategoriaGeral;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_financas.R;

import java.util.List;

public class CatGeralAdapter extends RecyclerView.Adapter<CatGeralAdapter.ViewHolder> {
    private List<CatGeral> catgerals;
    private OnItemClickListener listener;

    // Interface para capturar os cliques nos botões
    public interface OnItemClickListener {
        void onItemClick(CatGeral catgeral);
        void onEditarClick(CatGeral catgeral);
        void onExcluirClick(CatGeral catgeral);
    }

    public CatGeralAdapter(List<CatGeral> catgerals, OnItemClickListener listener) {
        this.catgerals = catgerals;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeCategoriaGeral;
        Button btnEditarCatGeral, btnExcluirCatGeral;

        public ViewHolder(View itemView){
            super(itemView);
            nomeCategoriaGeral = itemView.findViewById(R.id.textViewNomeCatGeral);
            btnEditarCatGeral = itemView.findViewById(R.id.buttonEditarCatGeral);
            btnExcluirCatGeral = itemView.findViewById(R.id.buttonExcluirCatGeral);
        }
        public void bind(CatGeral catgeral, OnItemClickListener listener){
            nomeCategoriaGeral.setText(catgeral.getNome_categoriaGeral());
            itemView.setOnClickListener(v -> listener.onItemClick(catgeral));
            btnEditarCatGeral.setOnClickListener(v -> listener.onEditarClick(catgeral));
            btnExcluirCatGeral.setOnClickListener(v -> listener.onExcluirClick(catgeral));
        }
    }
    @Override
    public CatGeralAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria_geral, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CatGeralAdapter.ViewHolder holder, int position) {
        holder.bind(catgerals.get(position), listener);
    }
    @Override
    public int getItemCount() {
        return catgerals.size();
    }

}
