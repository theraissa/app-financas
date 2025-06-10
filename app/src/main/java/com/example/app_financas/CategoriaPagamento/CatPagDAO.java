package com.example.app_financas.CategoriaPagamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app_financas.BancoHelper;
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
