package com.example.app_financas.CategoriaPagamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_financas.BancoHelper;
import com.example.app_financas.CategoriaGeral.CatGeral;

import java.util.ArrayList;
import java.util.List;

public class CatPagDAO {

    private SQLiteDatabase db;

    public CatPagDAO(Context context) {
        BancoHelper helper = new BancoHelper(context);
        db = helper.getWritableDatabase();
    }
    public long inserir(CatPag catPag){
        ContentValues values = new ContentValues();
        values.put("nome_categoriaPag", catPag.getNome_categoriaPag());
        return db.insert("CategoriaPagamento", null, values);
    }

    public void excluir(CatPag catPag) {
        db.delete("CategoriaPagamento", "id_categoriaPag = ?", new String[]{String.valueOf(catPag.getId_categoriaPag())});
    }

    public void atualizar(CatPag catPag) {
        ContentValues values = new ContentValues();
        values.put("nome_categoriaPag", catPag.getNome_categoriaPag());
        db.update("CategoriaPagamento", values, "id_categoriaPag = ?", new String[]{String.valueOf(catPag.getId_categoriaPag())});
    }

    public Integer buscarIdPorNome(String nome) {
        Integer id = null;
        Cursor cursor = db.rawQuery("SELECT id_categoriaPag FROM CategoriaPagamento WHERE nome_categoriaPag = ?", new String[]{nome});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id_categoriaPag"));
        }
        if (cursor != null) {
            cursor.close();
        }
        return id;
    }
    public String buscarNomePorId(int id) {
        String nome = null;
        Cursor cursor = db.query("CategoriaPagamento", new String[]{"nome_categoriaPag"},
                "id_categoriaPag = ?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            nome = cursor.getString(cursor.getColumnIndexOrThrow("nome_categoriaPag"));
            cursor.close();
        }
        return nome;
    }
    public List<CatPag> listar(){
        List<CatPag> catpags = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM CategoriaPagamento", null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            catpags.add(new CatPag(id, nome));
        }
        cursor.close();
        return catpags;
    }

}
