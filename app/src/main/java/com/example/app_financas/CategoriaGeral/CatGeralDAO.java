package com.example.app_financas.CategoriaGeral;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

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
    public Integer buscarIdPorNome(String nome) {
        Integer id = null;
        Cursor cursor = db.rawQuery("SELECT id_categoriaGeral FROM CategoriaGeral WHERE nome_categoriaGeral = ?", new String[]{nome});

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow("id_categoriaGeral"));
        }
        if (cursor != null) {
            cursor.close();
        }
        return id;
    }
    public String buscarNomePorId(int id) {
        String nome = null;
        Cursor cursor = db.query(
                "CategoriaGeral",
                new String[]{"nome_categoriaGeral"},
                "id_categoriaGeral = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            nome = cursor.getString(cursor.getColumnIndexOrThrow("nome_categoriaGeral"));
            cursor.close();
        }
        return nome;
    }
    public boolean catGeralTransacoesRelacionadas(int idCategoriaGeral) {
        String sql = "SELECT COUNT(*) FROM Transacao WHERE id_categoriaGeral = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idCategoriaGeral)});
        boolean temRelacionadas = false;

        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            temRelacionadas = count > 0;
        }
        cursor.close();
        return temRelacionadas;
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
