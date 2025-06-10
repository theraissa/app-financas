package com.example.app_financas.Perfil;
public class Perfil {
    private int id;
    private String nome;
    public Perfil() {
    }
    public Perfil(String nome) {
        this.nome = nome;
    }
    //METODO CONSTRUTOR
    public Perfil(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    //GETTERS E SETTERS
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
