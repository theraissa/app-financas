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
    public void setIdCategoriaGeral(Integer idCategoriaGeral) { this.idCategoriaGeral = idCategoriaGeral; }

    public Integer getIdCategoriaPagamento() { return idCategoriaPagamento; }
    public void setIdCategoriaPagamento(Integer idCategoriaPagamento) { this.idCategoriaPagamento = idCategoriaPagamento; }

    public Integer getIdCategoriaFormaPagamento() { return idCategoriaFormaPagamento; }
    public void setIdCategoriaFormaPagamento(Integer idCategoriaFormaPagamento) { this.idCategoriaFormaPagamento = idCategoriaFormaPagamento; }

    public String getNomeCategoriaGeral() {return nomeCategoriaGeral;}
    public void setNomeCategoriaGeral(String nomeCategoriaGeral) {this.nomeCategoriaGeral = nomeCategoriaGeral;}

    public String getNomeCategoriaPagamento() {return nomeCategoriaPagamento;}
    public void setNomeCategoriaPagamento(String nomeCategoriaPagamento) {this.nomeCategoriaPagamento = nomeCategoriaPagamento;}

    public String getNomeCategoriaFormaPagamento() {return nomeCategoriaFormaPagamento;}
    public void setNomeCategoriaFormaPagamento(String nomeCategoriaFormaPagamento) {this.nomeCategoriaFormaPagamento = nomeCategoriaFormaPagamento;}



}




