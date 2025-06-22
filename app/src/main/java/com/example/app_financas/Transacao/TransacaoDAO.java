package com.example.app_financas.Transacao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.app_financas.BancoHelper;

import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    private SQLiteDatabase db;

    public TransacaoDAO(Context context) {
        BancoHelper helper = new BancoHelper(context);
        db = helper.getWritableDatabase();
    }

    public long inserir(Transacao transacao) {
        ContentValues valores = new ContentValues();
        valores.put("valor", transacao.getValor());
        valores.put("descricao", transacao.getDescricao());
        valores.put("tipo", transacao.getTipo());
        valores.put("data", transacao.getData());

        if (transacao.getIdCategoriaGeral() != null)
            valores.put("id_categoriaGeral", transacao.getIdCategoriaGeral());
        if (transacao.getIdCategoriaPagamento() != null)
            valores.put("id_categoriaPag", transacao.getIdCategoriaPagamento());
        if (transacao.getIdCategoriaFormaPagamento() != null)
            valores.put("id_categoriaFormaPag", transacao.getIdCategoriaFormaPagamento());

        return db.insert("Transacao", null, valores);
    }

    public List<Transacao> buscarTransacoesComFiltros(String categoriaGeral, String categoriaFormaPag, String dataInicio, String dataFim, String tipo) {
        List<Transacao> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Transacao WHERE 1=1 ");
        List<String> args = new ArrayList<>();

        if (categoriaGeral != null) {
            sql.append("AND id_categoriaGeral = (SELECT id_categoriaGeral FROM CategoriaGeral WHERE nome_categoriaGeral = ?) ");
            args.add(categoriaGeral);
        }

        if (categoriaFormaPag != null) {
            sql.append("AND id_categoriaFormaPag = (SELECT id_categoriaFormaPag FROM CategoriaFormaPagamento WHERE nome_categoriaFormaPag = ?) ");
            args.add(categoriaFormaPag);
        }

        if (dataInicio != null) {
            sql.append("AND data >= ? ");
            args.add(dataInicio);
        }

        if (dataFim != null) {
            sql.append("AND data <= ? ");
            args.add(dataFim);
        }

        if (tipo != null) {
            sql.append("AND tipo = ? ");
            args.add(tipo);
        }

        Cursor cursor = db.rawQuery(sql.toString(), args.toArray(new String[0]));

        while (cursor.moveToNext()) {
            Transacao t = new Transacao();
            t.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id_transacao")));
            t.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow("valor")));
            t.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
            t.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));
            t.setData(cursor.getString(cursor.getColumnIndexOrThrow("data")));

            int colGeral = cursor.getColumnIndexOrThrow("id_categoriaGeral");
            int colForma = cursor.getColumnIndexOrThrow("id_categoriaFormaPag");

            t.setIdCategoriaGeral(cursor.isNull(colGeral) ? null : cursor.getInt(colGeral));
            t.setIdCategoriaFormaPagamento(cursor.isNull(colForma) ? null : cursor.getInt(colForma));

            lista.add(t);
        }
        cursor.close();

        return lista;
    }

    public List<Transacao> listar() {
        List<Transacao> lista = new ArrayList<>();

        String sql = "SELECT t.id_transacao, t.valor, t.descricao, t.tipo, t.data, " +
                "t.id_categoriaGeral, t.id_categoriaPag, t.id_categoriaFormaPag, " +
                "cg.nome_categoriaGeral, cp.nome_categoriaPag, cfp.nome_categoriaFormaPag " +
                "FROM Transacao t " +
                "LEFT JOIN CategoriaGeral cg ON t.id_categoriaGeral = cg.id_categoriaGeral " +
                "LEFT JOIN CategoriaPagamento cp ON t.id_categoriaPag = cp.id_categoriaPag " +
                "LEFT JOIN CategoriaFormaPagamento cfp ON t.id_categoriaFormaPag = cfp.id_categoriaFormaPag";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Transacao t = new Transacao();
            t.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id_transacao")));
            t.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow("valor")));
            t.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow("descricao")));
            t.setTipo(cursor.getString(cursor.getColumnIndexOrThrow("tipo")));
            t.setData(cursor.getString(cursor.getColumnIndexOrThrow("data")));

            // IDs
            int colGeral = cursor.getColumnIndexOrThrow("id_categoriaGeral");
            int colPag = cursor.getColumnIndexOrThrow("id_categoriaPag");
            int colForma = cursor.getColumnIndexOrThrow("id_categoriaFormaPag");

            t.setIdCategoriaGeral(cursor.isNull(colGeral) ? null : cursor.getInt(colGeral));
            t.setIdCategoriaPagamento(cursor.isNull(colPag) ? null : cursor.getInt(colPag));
            t.setIdCategoriaFormaPagamento(cursor.isNull(colForma) ? null : cursor.getInt(colForma));

            // Nomes das categorias
            t.setNomeCategoriaGeral(cursor.getString(cursor.getColumnIndexOrThrow("nome_categoriaGeral")));
            t.setNomeCategoriaPagamento(cursor.getString(cursor.getColumnIndexOrThrow("nome_categoriaPag")));
            t.setNomeCategoriaFormaPagamento(cursor.getString(cursor.getColumnIndexOrThrow("nome_categoriaFormaPag")));

            Log.d("DEBUG", "Geral: " + t.getNomeCategoriaGeral() + ", Pagamento: " + t.getNomeCategoriaPagamento() + ", Forma: " + t.getNomeCategoriaFormaPagamento());
            Log.d("SQL_RESULT", "CGeral: " + cursor.getString(cursor.getColumnIndexOrThrow("nome_categoriaGeral")));
            Log.d("SQL_RESULT", "CPag: " + cursor.getString(cursor.getColumnIndexOrThrow("nome_categoriaPag")));
            Log.d("SQL_RESULT", "CForma: " + cursor.getString(cursor.getColumnIndexOrThrow("nome_categoriaFormaPag")));

            lista.add(t);
        }

        cursor.close();
        return lista;
    }


}
