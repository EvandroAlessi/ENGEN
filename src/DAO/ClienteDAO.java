/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Cliente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * DAO Cliente
 * Responsável pela persistência dos Clientes
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see ClienteDAO
 */
public class ClienteDAO {

    private final Contexto contexto = new Contexto();

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public String[] getAllMetaData() throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM Clientes;";
        ResultSetMetaData fields = contexto.executeQuery(query).getMetaData();
        String[] columns = new String[fields.getColumnCount()];

        for (int i = 1; i <= fields.getColumnCount(); i++) {
            columns[i - 1] = fields.getColumnName(i);
        }

        return columns;
    }

    /**
     * Cria um novo cliente a partir do objeto recebido
     * @param cliente
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean create(Cliente cliente) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Clientes(CPF, Nome, DataNascimento, Pontuacao)values(?, ?, ?, ?)";
        try (PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparestatement.setString(1, cliente.getCPF());//substitui o ? pelo dado do usuario
            preparestatement.setString(2, cliente.getNome()); 
            preparestatement.setString(3, cliente.getDataNascimento().toString()); 
            preparestatement.setInt(4, cliente.getPontuacao());

            //executando comando sql
            int result = preparestatement.executeUpdate();
            if (result > 0) {
                ResultSet id = preparestatement.getGeneratedKeys();
                if (id.next()) {
                    cliente.setClienteID(id.getInt(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean exists(String cpf) throws ClassNotFoundException, SQLException {
        String query = "select ClienteID from Clientes where CPF = '" + cpf + "';";

        ResultSet dados = contexto.executeQuery(query);

        return dados.next();
    }
    
    public boolean exists(String cpf, int id) throws ClassNotFoundException, SQLException {
        String query = "select ClienteID from Clientes where CPF = '" + cpf + "' AND ClienteID != "+ id +";";

        ResultSet dados = contexto.executeQuery(query);

        return dados.next();
    }

    /** 
     * Busca o cliente a partir do id informado
     * 
     * @param id
     * @return 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Cliente get(int id) throws ClassNotFoundException, SQLException {
        String query = "select * from Clientes where ClienteID = '" + id + "';";
        Cliente cliente = new Cliente();
        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            cliente.setClienteID(dados.getInt("ClienteID"));
            cliente.setCPF(dados.getString("CPF"));
            cliente.setNome(dados.getString("Nome"));
            cliente.setDataNascimento(dados.getDate("DataNascimento").toLocalDate());
            cliente.setPontuacao(dados.getInt("Pontuacao"));
        }

        return cliente;
    }

    /**
     * Busca todos os clientes existentes no banco de dados
     *
     * @return ArrayList<Cliente> lista de todos os clientes
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Cliente> getAll() throws SQLException, ClassNotFoundException {
        String query = "select * from Clientes order by Nome;";
        ArrayList<Cliente> lista = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Cliente cliente = new Cliente();
            cliente.setClienteID(dados.getInt("ClienteID"));
            cliente.setCPF(dados.getString("CPF"));
            cliente.setNome(dados.getString("Nome"));
            cliente.setDataNascimento(dados.getDate("DataNascimento").toLocalDate());
            cliente.setPontuacao(dados.getInt("Pontuacao"));

            lista.add(cliente);
        }

        return lista;
    }

    /**
     * Atualiza a cliente a partir dos dados da mesma
     *
     * @param cliente Objeto do tipo cliente com todos os campos para a
     * atualização
     * @return true caso ocorra com sucesso; false caso não ocorra com sucesso;
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(Cliente cliente) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);

        columnsAndValues.append(" CPF= '")
                .append(cliente.getNome())
                .append("',");
        columnsAndValues.append(" Nome= '")
                .append(cliente.getDataNascimento())
                .append("',");
        columnsAndValues.append(" DataNascimento= '")
                .append(cliente.getPontuacao())
                .append("',");
        columnsAndValues.append(" Pontuacao= '")
                .append(cliente.getPontuacao())
                .append("'");

        String query = " update Clientes SET "
                + columnsAndValues.toString()
                + " WHERE ClienteID = " + cliente.getClienteID();

        int result = contexto.executeUpdate(query);

        return result > 0;
    }
    
    
    public boolean addPoints(int clienteID, int points) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);
        
        columnsAndValues.append(" Pontuacao= Pontuacao + ")
                .append(points)
                .append("");

        String query = " update Clientes SET "
                + columnsAndValues.toString()
                + " WHERE ClienteID = " + clienteID;

        int result = contexto.executeUpdate(query);

        return result > 0;
    }
    
    public boolean removePoints(int clienteID, int points) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);
        
        columnsAndValues.append(" Pontuacao= Pontuacao - ")
                .append(points)
                .append("");

        String query = " update Clientes SET "
                + columnsAndValues.toString()
                + " WHERE ClienteID = " + clienteID;

        int result = contexto.executeUpdate(query);

        return result > 0;
    }

    /**
     * Exclui uma cliente a partir do id indicado
     *
     * @param id id da cliente
     * @return true caso ocorra com sucesso; false caso não ocorra com sucesso;
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean delete(int id) throws ClassNotFoundException, SQLException {
        String sql = "delete from Clientes where ClienteID = ?";
        try (PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw e;
        }

        return true;
    }
}
