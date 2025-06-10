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
