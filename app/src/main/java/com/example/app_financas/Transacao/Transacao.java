package com.example.app_financas.Transacao;

public class Transacao {
    private int id;
    private double valor;
    private String descricao;
    private String tipo;
    private String data;
    private Integer idCategoriaGeral;
    private Integer idCategoriaPagamento;
    private Integer idCategoriaFormaPagamento;
    private String nomeCategoriaGeral;
    private String nomeCategoriaPagamento;
    private String nomeCategoriaFormaPagamento;

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public Integer getIdCategoriaGeral() { return idCategoriaGeral; }
    public void setIdCategoriaGeral(Integer id) { this.idCategoriaGeral = id; }

    public Integer getIdCategoriaPagamento() { return idCategoriaPagamento; }
    public void setIdCategoriaPagamento(Integer id) { this.idCategoriaPagamento = id; }

    public Integer getIdCategoriaFormaPagamento() { return idCategoriaFormaPagamento; }
    public void setIdCategoriaFormaPagamento(Integer id) { this.idCategoriaFormaPagamento = id; }

    public String getNomeCategoriaGeral() {return nomeCategoriaGeral;}
    public void setNomeCategoriaGeral(String nome) {this.nomeCategoriaGeral = nome;}

    public String getNomeCategoriaPagamento() {return nomeCategoriaPagamento;}
    public void setNomeCategoriaPagamento(String nome) {this.nomeCategoriaPagamento = nome;}

    public String getNomeCategoriaFormaPagamento() {return nomeCategoriaFormaPagamento;}
    public void setNomeCategoriaFormaPagamento(String nome) {this.nomeCategoriaFormaPagamento = nome;}



}




