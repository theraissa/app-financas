package com.example.app_financas.CategoriaFormaPag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_financas.BancoHelper;
import com.example.app_financas.CategoriaGeral.CatGeral;

import java.util.ArrayList;
import java.util.List;

public class CatFormaPagDAO {

    private SQLiteDatabase db;

    public CatFormaPagDAO(Context context) {
        BancoHelper helper = new BancoHelper(context);
        db = helper.getWritableDatabase();
    }
    public long inserir(CatFormaPag catFormaPag){
        ContentValues values = new ContentValues();
        values.put("nome_categoriaFormaPag", catFormaPag.getNome_categoriaFormaPag());
        return db.insert("CategoriaFormaPagamento", null, values);
    }
    public void excluir(CatFormaPag catFormaPag) {
        db.delete("CategoriaFormaPagamento", "id_categoriaFormaPag = ?", new String[]{String.valueOf(catFormaPag.getId_categoriaFormaPag())});
    }
    public void atualizar(CatFormaPag catFormaPag) {
        ContentValues values = new ContentValues();
        values.put("nome_categoriaFormaPag", catFormaPag.getNome_categoriaFormaPag());
        db.update("CategoriaFormaPagamento", values, "id_categoriaFormaPag = ?", new String[]{String.valueOf(catFormaPag.getId_categoriaFormaPag())});
    }

    public Integer buscarIdPorNome(String nome) {
        Integer id = null;
        Cursor cursor = db.rawQuery("SELECT id_categoriaFormaPag FROM CategoriaFormaPagamento WHERE nome_categoriaFormaPag = ?", new String[]{nome});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id_categoriaFormaPag"));
        }
        if (cursor != null) {
            cursor.close();
        }
        return id;
    }
    public String buscarNomePorId(int id) {
        String nome = null;
        Cursor cursor = db.query(
                "CategoriaFormaPagamento", new String[]{"nome_categoriaFormaPag"},
                "id_categoriaFormaPag = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            nome = cursor.getString(cursor.getColumnIndexOrThrow("nome_categoriaFormaPag"));
            cursor.close();
        }
        return nome;
    }
    public boolean catFormaPagTransacoesRelacionadas(int idCategoriaFormaPag) {
        String sql = "SELECT COUNT(*) FROM Transacao WHERE id_categoriaFormaPag = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idCategoriaFormaPag)});
        boolean temRelacionadas = false;

        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            temRelacionadas = count > 0;
        }
        cursor.close();
        return temRelacionadas;
    }
    public List<CatFormaPag> listar(){
        List<CatFormaPag> catformapags = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM CategoriaFormaPagamento", null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            catformapags.add(new CatFormaPag(id, nome));
        }
        cursor.close();
        return catformapags;
    }

}
