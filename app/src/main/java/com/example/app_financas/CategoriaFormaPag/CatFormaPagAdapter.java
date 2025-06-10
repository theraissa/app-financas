package com.example.app_financas.CategoriaFormaPag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_financas.R;

import java.util.List;

public class CatFormaPagAdapter extends RecyclerView.Adapter<CatFormaPagAdapter.ViewHolder>{

    private List<CatFormaPag> catformapags;
    private CatFormaPagAdapter.OnItemClickListener listenerFormaPag;
    public interface OnItemClickListener {
        void onItemClick(CatFormaPag catformapag);
    }
    public CatFormaPagAdapter(List<CatFormaPag> catformapags, CatFormaPagAdapter.OnItemClickListener listenerFormaPag) {
        this.catformapags = catformapags;
        this.listenerFormaPag = listenerFormaPag;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeCategoriaFormaPag;

        public ViewHolder(View itemView){
            super(itemView);
            nomeCategoriaFormaPag = itemView.findViewById(R.id.textViewNomeCatFormaPag);
        }
        public void bind(CatFormaPag catformapag, CatFormaPagAdapter.OnItemClickListener listenerFormaPag){
            nomeCategoriaFormaPag.setText(catformapag.getNome_categoriaFormaPag());
            itemView.setOnClickListener(v -> listenerFormaPag.onItemClick(catformapag));
        }
    }
    @Override
    public CatFormaPagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria_forma_pag, parent, false);
        return new CatFormaPagAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CatFormaPagAdapter.ViewHolder holder, int position) {
        holder.bind(catformapags.get(position), listenerFormaPag);
    }
    @Override
    public int getItemCount() {
        return catformapags.size();
    }

}
