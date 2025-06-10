package com.example.app_financas.CategoriaPagamento;

public class CatPag {
    private int id_categoriaPag;
    private String nome_categoriaPag;

    //METODO CONSTRUTOR
    public CatPag(int id_categoriaPag, String nome_categoriaPag) {
        this.id_categoriaPag = id_categoriaPag;
        this.nome_categoriaPag = nome_categoriaPag;
    }

    //GETTERS E SETTERS
    public int getId_categoriaPag() {
        return id_categoriaPag;
    }
    public void setId_categoriaPag(int id_categoriaPag) {
        this.id_categoriaPag = id_categoriaPag;
    }
    public String getNome_categoriaPag() {
        return nome_categoriaPag;
    }
    public void setNome_categoriaPag(String nome_categoriaPag) {
        this.nome_categoriaPag = nome_categoriaPag;
    }




}
