/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.Sala;
import Models.Sessao;
import Models.Filme;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * DAO Sessao
 * Responsável pela persistência das Sessoes
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Sessao
 * @see Sala
 * @see Filme
 */
public class SessaoDAO {

    private final Contexto contexto = new Contexto();

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public String[] getAllMetaData() throws ClassNotFoundException, SQLException {
        String query = "select * from Sessoes;";
        ResultSetMetaData fields = contexto.executeQuery(query).getMetaData();
        String[] columns = new String[fields.getColumnCount()];

        for (int i = 1; i <= fields.getColumnCount(); i++) {
            columns[i - 1] = fields.getColumnName(i);
        }

        return columns;
    }

    /**
     *
     * @param sessao
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean create(Sessao sessao) throws ClassNotFoundException, SQLException {
        String sql = "insert into Sessoes(FilmeID, SalaID, Ingressos, Data, ValorIngresso)values(?, ?, ?, ?, ?)";
        try (PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            preparestatement.setInt(1, sessao.getFilme().getFilmeID()); //substitui o ? pelo dado do usuario
            preparestatement.setInt(2, sessao.getSala().getSalaID());
            preparestatement.setInt(3, sessao.getIngressos());
            preparestatement.setTimestamp(4, Timestamp.valueOf(sessao.getData()));
            preparestatement.setDouble(5, sessao.getValorIngresso());
            
            System.out.println(sql);
            System.out.println(preparestatement.toString());
            //executando comando sql
            int result = preparestatement.executeUpdate();
            if (result > 0) {
                ResultSet id = preparestatement.getGeneratedKeys();
                if (id.next()) {
                    sessao.setSessaoID(id.getInt(1));
                    
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
    * @param salaID
    * @param data
    * @return
    * @throws ClassNotFoundException
    * @throws SQLException
    */
    public boolean exists(int salaID, LocalDateTime data) throws ClassNotFoundException, SQLException{
        String query = "select s.* from Sessoes AS s LEFT JOIN Filmes AS f ON (f.FilmeID = s.FilmeID) where SalaID = '"+ salaID +"' AND Data BETWEEN '"+ data +"' AND DATE_ADD('"+ data +"', INTERVAL + f.Duracao MINUTE);";
        //String query = "select s.* from Sessoes AS s INNER JOIN Filmes AS f where SalaID = '2' AND Data > ('2019-08-08 10:00:00' + f.Duracao);";
        System.out.println(query);
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
    public Sessao get(int id) throws ClassNotFoundException, SQLException {
        String query = "select * from Sessoes where SessaoID = '" + id + "';";
        Sessao sessao = new Sessao();
        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            sessao.setSessaoID(dados.getInt("SessaoID"));
            sessao.setFilmeID(dados.getInt("FilmeID"));
            sessao.setSalaID(dados.getInt("SalaID"));
            sessao.setIngressos(dados.getInt("Ingressos"));
            sessao.setData(dados.getTimestamp("Data").toLocalDateTime());
            sessao.setValorIngresso(dados.getDouble("ValorIngresso"));

            String queryFilme = "select * from Filmes where FilmeID = '"
                    + dados.getInt("FilmeID")
                    + "';";
            ResultSet dadosFilme = contexto.executeQuery(queryFilme);

            while (dadosFilme.next()) {
                sessao.setFilme(
                        new Filme(
                            dadosFilme.getInt("FilmeID"),
                            dadosFilme.getString("Titulo"),
                            dadosFilme.getString("Diretor"),
                            dadosFilme.getString("Genero"),
                            dadosFilme.getString("Idioma"),
                            dadosFilme.getInt("Duracao")
                ));
            }

            String querySala = "select * from Salas where SalaID = '"
                        + dados.getInt("SalaID")
                        + "';";
            ResultSet dadosSala = contexto.executeQuery(querySala);

            while (dadosSala.next()) {
                sessao.setSala((
                        new Sala(
                                dadosSala.getInt("SalaID"),
                                dadosSala.getInt("Numero"),
                                dadosSala.getInt("Capacidade")
                )));
            }
        }

        return sessao;
    }

    /**
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Sessao> getAll() throws ClassNotFoundException, SQLException {
        String query = "select * from Sessoes order by Data desc;";
        System.out.println(query);
        ArrayList<Sessao> list = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Sessao sessao = new Sessao();

            sessao.setSessaoID(dados.getInt("SessaoID"));
            sessao.setFilmeID(dados.getInt("FilmeID"));
            sessao.setSalaID(dados.getInt("SalaID"));
            sessao.setIngressos(dados.getInt("Ingressos"));
            sessao.setData(dados.getTimestamp("Data").toLocalDateTime());
            sessao.setValorIngresso(dados.getDouble("ValorIngresso"));

            String queryFilme = "select * from Filmes where FilmeID = '"
                    + dados.getInt("FilmeID")
                    + "';";
            ResultSet dadosFilme = contexto.executeQuery(queryFilme);

            while (dadosFilme.next()) {
                sessao.setFilme(
                        new Filme(
                            dadosFilme.getInt("FilmeID"),
                            dadosFilme.getString("Titulo"),
                            dadosFilme.getString("Diretor"),
                            dadosFilme.getString("Genero"),
                            dadosFilme.getString("Idioma"),
                            dadosFilme.getInt("Duracao")
                ));
            }

            String querySala = "select * from Salas where SalaID = '"
                        + dados.getInt("SalaID")
                        + "';";
            ResultSet dadosSala = contexto.executeQuery(querySala);

            while (dadosSala.next()) {
                sessao.setSala((
                        new Sala(
                                dadosSala.getInt("SalaID"),
                                dadosSala.getInt("Numero"),
                                dadosSala.getInt("Capacidade")
                )));
            }

            list.add(sessao);
        }

        return list;
    }
    
    /**
     *
     * @param titulo
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Sessao> getAllSessionsByMovieTitle(String titulo) throws ClassNotFoundException, SQLException {
        String query = "select s.* from Sessoes AS s LEFT JOIN Filmes AS f ON (f.FilmeID = s.FilmeID) WHERE f.Titulo like '%"+ titulo +"%' order by f.Titulo asc, s.Data desc;";
        System.out.println(query);
        ArrayList<Sessao> list = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Sessao sessao = new Sessao();

            sessao.setSessaoID(dados.getInt("SessaoID"));
            sessao.setFilmeID(dados.getInt("FilmeID"));
            sessao.setSalaID(dados.getInt("SalaID"));
            sessao.setIngressos(dados.getInt("Ingressos"));
            sessao.setData(dados.getTimestamp("Data").toLocalDateTime());
            sessao.setValorIngresso(dados.getDouble("ValorIngresso"));

            String queryFilme = "select * from Filmes where FilmeID = '"
                    + dados.getInt("FilmeID")
                    + "';";
            ResultSet dadosFilme = contexto.executeQuery(queryFilme);

            while (dadosFilme.next()) {
                sessao.setFilme(
                        new Filme(
                            dadosFilme.getInt("FilmeID"),
                            dadosFilme.getString("Titulo"),
                            dadosFilme.getString("Diretor"),
                            dadosFilme.getString("Genero"),
                            dadosFilme.getString("Idioma"),
                            dadosFilme.getInt("Duracao")
                ));
            }

            String querySala = "select * from Salas where SalaID = '"
                        + dados.getInt("SalaID")
                        + "';";
            ResultSet dadosSala = contexto.executeQuery(querySala);

            while (dadosSala.next()) {
                sessao.setSala((
                        new Sala(
                                dadosSala.getInt("SalaID"),
                                dadosSala.getInt("Numero"),
                                dadosSala.getInt("Capacidade")
                )));
            }

            list.add(sessao);
        }

        return list;
    }

    /**
     *
     * @param sessao
     * @param Sessao
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(Sessao sessao) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);

        columnsAndValues.append(" FilmeID= '")
                .append(sessao.getFilmeID())
                .append("',");
        columnsAndValues.append(" SalaID= '")
                .append(sessao.getSalaID())
                .append("',");
        columnsAndValues.append(" Ingressos= '")
                .append(sessao.getIngressos())
                .append("',");
        columnsAndValues.append(" Data= '")
                .append(sessao.getData())
                .append("',");
        columnsAndValues.append(" ValorIngresso= ")
                .append(sessao.getValorIngresso());
 System.out.println(columnsAndValues.toString());
        String query = " update Sessoes SET "
                + columnsAndValues.toString()
                + " WHERE SessaoID = " + sessao.getSessaoID();

       
        
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
        String sql = "delete from Sessoes where SessaoID = ?";
        try (PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw e;
        }

        return true;
    }
}
