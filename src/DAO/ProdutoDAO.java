/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Produto;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * DAO Produto
 * Responsável pela persistência dos Produtos
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see ProdutoDAO
 */
public class ProdutoDAO {

    private final Contexto contexto = new Contexto();

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public String[] getAllMetaData() throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM Produtos;";
        ResultSetMetaData fields = contexto.executeQuery(query).getMetaData();
        String[] columns = new String[fields.getColumnCount()];

        for (int i = 1; i <= fields.getColumnCount(); i++) {
            columns[i - 1] = fields.getColumnName(i);
        }

        return columns;
    }

    /**
     * Cria um novo produto a partir do objeto recebido
     * @param produto
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean create(Produto produto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Produtos(Descricao, Preco, PrecoPontuacao)values(?, ?, ?)";
        try (PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparestatement.setString(1, produto.getDescricao());//substitui o ? pelo dado do usuario
            preparestatement.setDouble(2, produto.getPreco()); 
            preparestatement.setInt(3, produto.getPrecoPontuacao()); 

            //executando comando sql
            int result = preparestatement.executeUpdate();
            if (result > 0) {
                ResultSet id = preparestatement.getGeneratedKeys();
                if (id.next()) {
                    produto.setProdutoID(id.getInt(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean exists(String descricao) throws ClassNotFoundException, SQLException {
        String query = "select ProdutoID from Produtos where Descricao = '" + descricao + "';";

        ResultSet dados = contexto.executeQuery(query);

        return dados.next();
    }
    
    public boolean exists(String descricao, int id) throws ClassNotFoundException, SQLException {
        String query = "select ProdutoID from Produtos where Descricao = '" + descricao + "' AND ProdutoID != "+ id +";";

        ResultSet dados = contexto.executeQuery(query);

        return dados.next();
    }

    /** 
     * Busca o produto a partir do id informado
     * 
     * @param id
     * @return 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Produto get(int id) throws ClassNotFoundException, SQLException {
        String query = "select * from Produtos where ProdutoID = '" + id + "';";
        Produto produto = new Produto();
        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            produto.setProdutoID(dados.getInt("ProdutoID"));
            produto.setDescricao(dados.getString("Descricao"));
            produto.setPreco(dados.getDouble("Preco"));
            produto.setPrecoPontuacao(dados.getInt("PrecoPontuacao"));
        }

        return produto;
    }

    /**
     * Busca todos os produtos existentes no banco de dados
     *
     * @return ArrayList<Produto> lista de todos os produtos
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Produto> getAll() throws SQLException, ClassNotFoundException {
        String query = "select * from Produtos order by Preco;";
        ArrayList<Produto> lista = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Produto produto = new Produto();
            produto.setProdutoID(dados.getInt("ProdutoID"));
            produto.setDescricao(dados.getString("Descricao"));
            produto.setPreco(dados.getDouble("Preco"));
            produto.setPrecoPontuacao(dados.getInt("PrecoPontuacao"));

            lista.add(produto);
        }

        return lista;
    }

    /**
     * Atualiza a produto a partir dos dados da mesma
     *
     * @param produto Objeto do tipo produto com todos os campos para a
     * atualização
     * @return true caso ocorra com sucesso; false caso não ocorra com sucesso;
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(Produto produto) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);

        columnsAndValues.append(" Descricao= '")
                .append(produto.getPreco())
                .append("',");
        columnsAndValues.append(" Preco= '")
                .append(produto.getPrecoPontuacao())
                .append("',");
        columnsAndValues.append(" PrecoPontuacao= '")
                .append(produto.getPrecoPontuacao())
                .append("'");

        String query = " update Produtos SET "
                + columnsAndValues.toString()
                + " WHERE ProdutoID = " + produto.getProdutoID();

        int result = contexto.executeUpdate(query);

        return result > 0;
    }

    /**
     * Exclui uma produto a partir do id indicado
     *
     * @param id id da produto
     * @return true caso ocorra com sucesso; false caso não ocorra com sucesso;
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean delete(int id) throws ClassNotFoundException, SQLException {
        String sql = "delete from Produtos where ProdutoID = ?";
        try (PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw e;
        }

        return true;
    }
}
