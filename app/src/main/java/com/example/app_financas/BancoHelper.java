package com.example.app_financas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "financas.db";
    private static final int DATABASE_VERSION = 9;
    public BancoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //tabela perfil
        String sqlPerfil = "CREATE TABLE Perfil (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL);";
        db.execSQL(sqlPerfil);

        //tabela categoria geral
        String sqlCategoriaGeral = "CREATE TABLE CategoriaGeral (" +
                "id_categoriaGeral INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome_categoriaGeral TEXT NOT NULL);";
        db.execSQL(sqlCategoriaGeral);

        //tabela categoria pagamento
        String sqlCategoriaPagamento = "CREATE TABLE CategoriaPagamento (" +
                "id_categoriaPag INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome_categoriaPag TEXT NOT NULL);";
        db.execSQL(sqlCategoriaPagamento);

        //tabela categoria forma pagamento
        String sqlCategoriaFormaPagamento = "CREATE TABLE CategoriaFormaPagamento (" +
                "id_categoriaFormaPag INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome_categoriaFormaPag TEXT NOT NULL);";
        db.execSQL(sqlCategoriaFormaPagamento);

        //tabela transações
        String sqlTransacao = "CREATE TABLE Transacao (" +
                "id_transacao INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "valor REAL, " +
                "descricao TEXT, " +
                "tipo TEXT, " +
                "data TEXT, " +
                "id_categoriaGeral INTEGER, " +
                "id_categoriaPag INTEGER, " +
                "id_categoriaFormaPag INTEGER, " +
                "FOREIGN KEY (id_categoriaGeral) REFERENCES CategoriaGeral(id_categoriaGeral), " +
                "FOREIGN KEY (id_categoriaPag) REFERENCES CategoriaPagamento(id_categoriaPag), " +
                "FOREIGN KEY (id_categoriaFormaPag) REFERENCES CategoriaFormaPagamento(id_categoriaFormaPag)" +
                ");";
        db.execSQL(sqlTransacao);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Perfil");
        db.execSQL("DROP TABLE IF EXISTS CategoriaGeral");
        db.execSQL("DROP TABLE IF EXISTS CategoriaPagamento");
        db.execSQL("DROP TABLE IF EXISTS CategoriaFormaPagamento");
        db.execSQL("DROP TABLE IF EXISTS Transacao");
        onCreate(db);
    }
}
