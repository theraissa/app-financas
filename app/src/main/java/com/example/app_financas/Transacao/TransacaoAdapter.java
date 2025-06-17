package com.example.app_financas.Transacao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.app_financas.R;

import java.util.List;

public class TransacaoAdapter extends RecyclerView.Adapter<TransacaoAdapter.ViewHolder> {

    private List<Transacao> transacaos;
    private TransacaoAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Transacao transacao);
    }
    public TransacaoAdapter(List<Transacao> transacaos, TransacaoAdapter.OnItemClickListener listener) {
        this.transacaos = transacaos;
        this.listener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView valor, desc, data, filtro;
        public ViewHolder(View itemView) {
            super(itemView);
            valor = itemView.findViewById(R.id.textViewItemValor);
            desc = itemView.findViewById(R.id.textViewItemDesc);
            data = itemView.findViewById(R.id.textViewItemData);
            filtro = itemView.findViewById(R.id.textViewItemFiltros);
        }
        public void bind(Transacao transacao, TransacaoAdapter.OnItemClickListener listener) {
            valor.setText(String.format("R$ %.2f", transacao.getValor()));
            desc.setText(transacao.getDescricao());
            data.setText(transacao.getData());
            String categorias = transacao.getNomeCategoriaGeral() + " | " +
                    transacao.getNomeCategoriaPagamento() + " | " +
                    transacao.getNomeCategoriaFormaPagamento();
            filtro.setText(categorias);

            itemView.setOnClickListener(v -> listener.onItemClick(transacao));
        }
    }
    public void atualizarLista(List<Transacao> novaLista) {
        this.transacaos = novaLista;
        notifyDataSetChanged();
    }
    @Override
    public TransacaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_valor, parent, false);
        return new TransacaoAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(TransacaoAdapter.ViewHolder holder, int position) {
        holder.bind(transacaos.get(position), listener);
    }
    @Override
    public int getItemCount() {
        return transacaos.size();
    }




}
