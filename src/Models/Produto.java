/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 * Modelo estrutural de Filme.
 * Gera objetos modelos para comunicação com BD ou utilização na GUI.
 * @author Evandro Alessi
 * @author Eric Ueta
 */
public class Produto {
    private int produtoID;
    private String descricao;
    private double preco;
    private int precoPontuacao;

    public Produto(int produtoID, String descricao, double preco, int precoPontuacao) {
        this.produtoID = produtoID;
        this.descricao = descricao;
        this.preco = preco;
        this.precoPontuacao = precoPontuacao;
    }

    public Produto(String descricao, double preco, int precoPontuacao) {
        this.descricao = descricao;
        this.preco = preco;
        this.precoPontuacao = precoPontuacao;
    }

    public Produto() {
    }

    
    public int getProdutoID() {
        return produtoID;
    }

    public void setProdutoID(int produtoID) {
        this.produtoID = produtoID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String CPF) {
        this.descricao = CPF;
    }
    
    public double getPreco() {
        return preco;
    }

    public void setPreco(double nome) {
        this.preco = nome;
    }

    public int getPrecoPontuacao() {
        return precoPontuacao;
    }

    public void setPrecoPontuacao(int precoPontuacao) {
        this.precoPontuacao = precoPontuacao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}
