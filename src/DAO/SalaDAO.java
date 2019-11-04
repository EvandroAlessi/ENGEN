/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Sala;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO Sala
 * Responsável pela persistência das Salas
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Sala
 */
public class SalaDAO {

    private final Contexto contexto = new Contexto();

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public String[] getAllMetaData() throws ClassNotFoundException, SQLException {
        String query = "select * from Salas;";
        ResultSetMetaData fields = contexto.executeQuery(query).getMetaData();
        String[] columns = new String[fields.getColumnCount()];

        for (int i = 1; i <= fields.getColumnCount(); i++) {
            columns[i - 1] = fields.getColumnName(i);
        }

        return columns;
    }

    /**
     *
     * @param sala
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean create(Sala sala) throws ClassNotFoundException, SQLException {
        String sql = "insert into Salas(Numero, Capacidade)values(?, ?)";
        try (PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparestatement.setInt(1, sala.getNumero());//substitui o ? pelo dado do usuario
            preparestatement.setInt(2, sala.getCapacidade());

            //executando comando sql
            int result = preparestatement.executeUpdate();
            if (result > 0) {
                ResultSet id = preparestatement.getGeneratedKeys();
                if (id.next()) {
                    sala.setSalaID(id.getInt(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
    *
    * @param numero
    * @return
    * @throws ClassNotFoundException
    * @throws SQLException
    */
    public boolean exists(int numero) throws ClassNotFoundException, SQLException{
        String query = "select * from Salas where Numero = '"+ numero +"';";

        ResultSet dados = contexto.executeQuery(query);

        return dados.next();
    }
    
    public boolean exists(int numero, int id) throws ClassNotFoundException, SQLException{
        String query = "select * from Salas where Numero = "+ numero +" AND SalaID != "+ id +";";

        ResultSet dados = contexto.executeQuery(query);

        return dados.next();
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public int getCapacidade(int id) throws ClassNotFoundException, SQLException {
        String query = "select Capacidade from Salas where SalaID = '" + id + "';";
        int capacidade = -1;
        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            capacidade = dados.getInt("Capacidade");
        }

        return capacidade;
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Sala get(int id) throws ClassNotFoundException, SQLException {
        String query = "select * from Salas where SalaID = '" + id + "';";
        Sala sala = new Sala();
        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            sala.setSalaID(dados.getInt("SalaID"));
            sala.setNumero(dados.getInt("Numero"));
            sala.setCapacidade(dados.getInt("Capacidade"));
        }

        return sala;
    }

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Sala> getAll() throws ClassNotFoundException, SQLException {
        String query = "select * from Salas";
        ArrayList<Sala> lista = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Sala sala = new Sala();
            sala.setSalaID(dados.getInt("SalaID"));
            sala.setNumero(dados.getInt("Numero"));
            sala.setCapacidade(dados.getInt("Capacidade"));

            lista.add(sala);
        }

        return lista;
    }

    /**
     *
     * @param sala
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(Sala sala) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);

        columnsAndValues.append(" Numero= '")
                .append(sala.getNumero())
                .append("',");
        columnsAndValues.append(" Capacidade= ")
                .append(sala.getCapacidade());

        String query = " update Salas SET "
                + columnsAndValues.toString()
                + " WHERE SalaID = " + sala.getSalaID();

        int result = contexto.executeUpdate(query);

        return result > 0;
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean delete(int id) throws ClassNotFoundException, SQLException {
        String sql = "delete from Salas where SalaID = ?";
        try (PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw e;
        }

        return true;
    }
}
