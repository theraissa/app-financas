package com.example.app_financas.CategoriaFormaPag;

public class CatFormaPag {
    private int id_categoriaFormaPag;
    private String nome_categoriaFormaPag;

    //MÉTODO CONSTRUTOR
    public CatFormaPag(int id_categoriaFormaPag, String nome_categoriaFormaPag) {
        this.id_categoriaFormaPag = id_categoriaFormaPag;
        this.nome_categoriaFormaPag = nome_categoriaFormaPag;
    }

    //GETTERS E SETTERS
    public int getId_categoriaFormaPag() {
        return id_categoriaFormaPag;
    }
    public void setId_categoriaFormaPag(int id_categoriaFormaPag) {
        this.id_categoriaFormaPag = id_categoriaFormaPag;
    }

    public String getNome_categoriaFormaPag() {
        return nome_categoriaFormaPag;
    }
    public void setNome_categoriaFormaPag(String nome_categoriaFormaPag) {
        this.nome_categoriaFormaPag = nome_categoriaFormaPag;
    }
}
