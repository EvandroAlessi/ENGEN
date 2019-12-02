/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import DAO.ProdutoDAO;
import Models.Produto;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador de Produtos
 * Propicia a comunicação entre a GUI e DAO
 * Implementa a lógica de negócios
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Produto
 * @see ProdutoDAO
 */
public class ProdutoController {

    /**
     *
     * @param produto
     * @return
     */
    public Produto create(Produto produto) {
        try {
            ProdutoDAO dao = new ProdutoDAO();
            
            if (produto.getDescricao() != null && produto.getDescricao().length() > 0) 
                if (produto.getPreco() >= 0) 
                    if (produto.getPrecoPontuacao() >= 0) 
                        if (!dao.exists(produto.getDescricao())) 
                            if (dao.create(produto)) 
                                return produto;
                            else 
                                Mensagem.aviso("Não foi possivel cadastrar o produto.");
                        else
                            Mensagem.aviso("Já existe um Produto cadastrado com essa Descricao.");
                    else
                        Mensagem.aviso("Não é possível registrar uma pontuação negativa.");
                else 
                    Mensagem.aviso("Não é possível registrar um preço negativo.");
            else 
                Mensagem.aviso("O produto deve ter uma Descricao.");
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public Produto get(int id) {
        try {
            Produto produto = new ProdutoDAO().get(id);

            return produto;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<Produto> getAll() {
        try {
            ArrayList<Produto> produtos = new ProdutoDAO().getAll();

            return produtos;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }
    
    
    /**
     *
     * @param produto
     * @return
     */
    public Produto update(Produto produto) {
        try {
            ProdutoDAO dao = new ProdutoDAO();

             if (produto.getDescricao() != null && produto.getDescricao().length() > 0) 
                if (produto.getPreco() >= 0) 
                    if (produto.getPrecoPontuacao() >= 0) 
                        if (!dao.exists(produto.getDescricao(), produto.getProdutoID())) 
                            if (dao.update(produto)) 
                                return produto;
                            else 
                                Mensagem.aviso("Não foi possivel atualizar o produto.");
                        else
                            Mensagem.aviso("Já existe um Produto cadastrado com essa Descricao.");
                    else
                        Mensagem.aviso("Não é possível registrar uma pontuação negativa.");
                else 
                    Mensagem.aviso("Não é possível registrar um preço negativo.");
            else 
                Mensagem.aviso("O produto deve ter uma Descricao.");
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean delete(int id) {
        try {
            if (id != 0) {
                return new ProdutoDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return false;
    }
}
