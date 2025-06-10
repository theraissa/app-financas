package com.example.app_financas.CategoriaGeral;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import com.example.app_financas.BancoHelper;

import java.util.ArrayList;
import java.util.List;

public class CatGeralDAO {

    private SQLiteDatabase db;

    public CatGeralDAO(Context context) {
        BancoHelper helper = new BancoHelper(context);
        db = helper.getWritableDatabase();
    }
    public long inserir(CatGeral catGeral){
        ContentValues values = new ContentValues();
        values.put("nome_categoriaGeral", catGeral.getNome_categoriaGeral());
        return db.insert("CategoriaGeral", null, values);
    }
    public void excluir(CatGeral catGeral) {
        db.delete("CategoriaGeral", "id_categoriaGeral = ?", new String[]{String.valueOf(catGeral.getId_categoriaGeral())});
    }
    public void atualizar(CatGeral catGeral) {
        ContentValues values = new ContentValues();
        values.put("nome_categoriaGeral", catGeral.getNome_categoriaGeral());
        db.update("CategoriaGeral", values, "id_categoriaGeral = ?", new String[]{String.valueOf(catGeral.getId_categoriaGeral())});
    }
    public List<CatGeral> listar(){
        List<CatGeral> catgerals = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM CategoriaGeral", null);

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            catgerals.add(new CatGeral(id, nome));
        }
        cursor.close();
        return catgerals;
    }
}
