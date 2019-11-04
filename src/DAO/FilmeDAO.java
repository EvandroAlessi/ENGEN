/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Filme;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO Filme
 * Responsável pela persistência dos Filmes
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see FilmeDAO
 */
public class FilmeDAO {

    private final Contexto contexto = new Contexto();

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public String[] getAllMetaData() throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM Filmes;";
        ResultSetMetaData fields = contexto.executeQuery(query).getMetaData();
        String[] columns = new String[fields.getColumnCount()];

        for (int i = 1; i <= fields.getColumnCount(); i++) {
            columns[i - 1] = fields.getColumnName(i);
        }

        return columns;
    }

    /**
     * Cria um novo filme a partir do objeto recebido
     * @param filme
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean create(Filme filme) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Filmes(Titulo, Diretor, Genero, Idioma, Duracao)values(?,?,?,?,?)";
        try (PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparestatement.setString(1, filme.getTitulo()); //substitui o ? pelo dado do usuario
            preparestatement.setString(2, filme.getDiretor()); 
            preparestatement.setString(3, filme.getGenero());
            preparestatement.setString(4, filme.getIdioma());
            preparestatement.setInt(5, filme.getDuracao());

            //executando comando sql
            int result = preparestatement.executeUpdate();
            if (result > 0) {
                ResultSet id = preparestatement.getGeneratedKeys();
                if (id.next()) {
                    filme.setFilmeID(id.getInt(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean exists(String titulo) throws ClassNotFoundException, SQLException {
        String query = "select FilmeID from Filmes where Titulo like '" + titulo + "';";

        ResultSet dados = contexto.executeQuery(query);

        return dados.next();
    }
    
    public boolean exists(String titulo, int id) throws ClassNotFoundException, SQLException {
        String query = "select FilmeID from Filmes where Titulo like '" + titulo + "' AND FilmeID != "+ id +";";

        ResultSet dados = contexto.executeQuery(query);

        return dados.next();
    }


    public ArrayList<Filme> find(String titulo) throws SQLException, ClassNotFoundException {
        String query = "select f.* from Filmes AS f RIGHT JOIN Sessoes AS s ON (f.FilmeID = s.FilmeID) WHERE f.Titulo like '%"+ titulo +"%' order by f.Titulo;";
        ArrayList<Filme> lista = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Filme filme = new Filme();
            
            filme.setFilmeID(dados.getInt("FilmeID"));
            filme.setTitulo(dados.getString("Titulo"));
            filme.setDiretor(dados.getString("Diretor"));
            filme.setGenero(dados.getString("Genero"));
            filme.setIdioma(dados.getString("Idioma"));
            filme.setDuracao (dados.getInt("Duracao"));

            lista.add(filme);
        }

        return lista;
    }

    /** 
     * Busca o filme a partir do id informado
     * 
     * @param id
     * @return 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Filme get(int id) throws ClassNotFoundException, SQLException {
        String query = "select * from Filmes where FilmeID = '" + id + "';";
        Filme filme = new Filme();
        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            filme.setFilmeID(dados.getInt("FilmeID"));
            filme.setTitulo(dados.getString("Titulo"));
            filme.setDiretor(dados.getString("Diretor"));
            filme.setGenero(dados.getString("Genero"));
            filme.setIdioma(dados.getString("Idioma"));
            filme.setDuracao(dados.getInt("Duracao"));
        }

        return filme;
    }

    /**
     * Busca todos os filmes existentes no banco de dados
     *
     * @return ArrayList<Filme> lista de todos os filmes
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Filme> getAll() throws SQLException, ClassNotFoundException {
        String query = "select * from Filmes order by Titulo;";
        ArrayList<Filme> lista = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Filme filme = new Filme();
            filme.setFilmeID(dados.getInt("FilmeID"));
            filme.setTitulo(dados.getString("Titulo"));
            filme.setDiretor(dados.getString("Diretor"));
            filme.setGenero(dados.getString("Genero"));
            filme.setIdioma(dados.getString("Idioma"));
            filme.setDuracao (dados.getInt("Duracao"));

            lista.add(filme);
        }

        return lista;
    }


    /**
     * Busca todos os filmes existentes no banco de dados
     *
     * @param Diretor 
     * @return ArrayList<Filme> lista de todaos os filmes
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Filme> getAll(String diretor) throws SQLException, ClassNotFoundException {
        String query = "select * from Filmes where Diretor like '"+ diretor +"' order by Titulo;";
        ArrayList<Filme> lista = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Filme filme = new Filme();
            filme.setFilmeID(dados.getInt("FilmeID"));
            filme.setTitulo(dados.getString("Titulo"));
            filme.setDiretor(dados.getString("Diretor"));
            filme.setGenero(dados.getString("Genero"));
            filme.setIdioma(dados.getString("Idioma"));
            filme.setDuracao (dados.getInt("Duracao"));

            lista.add(filme);
        }

        return lista;
    }

    /**
     * Busca todas as filmes existentes no banco de dados
     *
     * @param Diretor 
     * @return ArrayList<Filme> lista de todas as filme
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Filme> getAllInCartaz() throws SQLException, ClassNotFoundException {
        String query = "select f.* from Filmes AS f RIGHT JOIN Sessoes AS s ON (f.FilmeID = s.FilmeID) order by Titulo;";
        ArrayList<Filme> lista = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Filme filme = new Filme();
            filme.setFilmeID(dados.getInt("FilmeID"));
            filme.setTitulo(dados.getString("Titulo"));
            filme.setDiretor(dados.getString("Diretor"));
            filme.setGenero(dados.getString("Genero"));
            filme.setIdioma(dados.getString("Idioma"));
            filme.setDuracao (dados.getInt("Duracao"));

            lista.add(filme);
        }

        return lista;
    }

    /**
     * Atualiza a filme a partir dos dados da mesma
     *
     * @param filme Objeto do tipo filme com todos os campos para a
     * atualização
     * @return true caso ocorra com sucesso; false caso não ocorra com sucesso;
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(Filme filme) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);

        columnsAndValues.append(" Titulo= '")
                .append(filme.getTitulo())
                .append("',");
        columnsAndValues.append(" Diretor= '")
                .append(filme.getDiretor())
                .append("',");
        columnsAndValues.append(" Genero= '")
                .append(filme.getGenero())
                .append("',");
        columnsAndValues.append(" Idioma= '")
                .append(filme.getIdioma())
                .append("',");
        columnsAndValues.append(" Duracao= '")
                .append(filme.getDuracao())
                .append("'");

        String query = " update Filmes SET "
                + columnsAndValues.toString()
                + " WHERE FilmeID = " + filme.getFilmeID();

        int result = contexto.executeUpdate(query);

        return result > 0;
    }

    /**
     * Exclui uma filme a partir do id indicado
     *
     * @param id id da filme
     * @return true caso ocorra com sucesso; false caso não ocorra com sucesso;
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean delete(int id) throws ClassNotFoundException, SQLException {
        String sql = "delete from Filmes where FilmeID = ?";
        try (PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw e;
        }

        return true;
    }
}
