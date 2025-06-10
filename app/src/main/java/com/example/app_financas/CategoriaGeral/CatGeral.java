package com.example.app_financas.CategoriaGeral;

public class CatGeral {

    private int id_categoriaGeral;
    private String nome_categoriaGeral;

    //METODO CONSTRUTOR
    public CatGeral(int id_categoriaGeral, String nome_categoriaGeral) {
        this.id_categoriaGeral = id_categoriaGeral;
        this.nome_categoriaGeral = nome_categoriaGeral;
    }

    //GETTERS E SETTERS
    public int getId_categoriaGeral() {
        return id_categoriaGeral;
    }
    public void setId_categoriaGeral(int id_categoriaGeral) {
        this.id_categoriaGeral = id_categoriaGeral;
    }
    public String getNome_categoriaGeral() {
        return nome_categoriaGeral;
    }
    public void setNome_categoriaGeral(String nome_categoriaGeral) {
        this.nome_categoriaGeral = nome_categoriaGeral;
    }
}
