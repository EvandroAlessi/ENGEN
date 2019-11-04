/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DAO Contexto
 * Responsável por gerênciar a conexão com o BD
 * @author Evandro Alessi
 * @author Eric Ueta
 */
public class Contexto {

    private String url = "jdbc:mysql://localhost:3306/Cinema?useTimezone=true&serverTimezone=Brazil/East&useUnicode=true&characterEncoding=utf8";
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String usuario = "root";
    private String senha = "";
    private static Connection conexao;

    public void getConnection() throws ClassNotFoundException, SQLException {
        if (Contexto.conexao == null || Contexto.conexao.isClosed()) {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha); // conecta com o banco
        }
    }

    public int executeUpdate(String sql) throws ClassNotFoundException, SQLException {
        getConnection();
        Statement sessao = getConexao().createStatement();

        return sessao.executeUpdate(sql);
    }

    public ResultSet executeQuery(String query) throws ClassNotFoundException, SQLException {
        getConnection();
        Statement sessao = getConexao().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );

        return sessao.executeQuery(query);
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the conexao
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public Connection getConexao() throws ClassNotFoundException, SQLException {
        if (Contexto.conexao == null || Contexto.conexao.isClosed()) {
            getConnection();
        }
        return conexao;
    }

    /**
     * @param conexao the conexao to set
     */
    public void setConexao(Connection conexao) {
        Contexto.conexao = conexao;
    }
}
