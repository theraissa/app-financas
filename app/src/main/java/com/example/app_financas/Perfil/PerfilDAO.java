package com.example.app_financas.Perfil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.app_financas.BancoHelper;

import java.util.ArrayList;
import java.util.List;

public class PerfilDAO {
    private SQLiteDatabase db;

    public PerfilDAO(Context context) {
        BancoHelper helper = new BancoHelper(context);
        db = helper.getWritableDatabase(); //pega as informações no bancohelper
    }

    public long inserir(Perfil perfil) {
        int quantidade = quantidade();

        if (quantidade == 0) {
            ContentValues values = new ContentValues();
            values.put("nome", perfil.getNome());
            return db.insert("Perfil", null, values);
        } else {
            return 0;
        }
    }
    public void remover() {
        db.delete("Perfil", null, null); // remove todos
    }
    public Perfil selecionar() {
        Perfil perfil = null;
        Cursor cursor = db.rawQuery("SELECT * FROM Perfil", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            perfil = new Perfil(id, nome);
        }
        return perfil;
    }

    public void atualizar(Perfil perfil) {
        ContentValues values = new ContentValues();
        values.put("nome", perfil.getNome());

        db.update("Perfil", values, "id = ?", new String[]{String.valueOf(perfil.getId())});
    }

    public List<Perfil> listar() {
        List<Perfil> usuarios = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Perfil", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            usuarios.add(new Perfil(id, nome));
        }

        cursor.close();
        return usuarios;
    }

    public int quantidade() {
        List<Perfil> usuarios = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Perfil", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            Log.d("testeID", String.valueOf(id));
            String nome = cursor.getString(1);
            usuarios.add(new Perfil(id, nome));
        }

        cursor.close();
        return usuarios.size();
    }
}
