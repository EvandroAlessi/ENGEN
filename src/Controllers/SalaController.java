/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import DAO.SalaDAO;
import Models.Sala;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controlador de Sala
 * Propicia a comunicação entre a GUI e DAO
 * Implementa a lógica de negócios
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Sala
 * @see SalaDAO
 */
public class SalaController {

    /**
     *
     * @param sala
     * @return
     */
    public Sala create(Sala sala) {
        try {
            SalaDAO dao = new SalaDAO();

            if (sala.getNumero() > 0) {
                if (sala.getCapacidade() > 0) {
                    if (!dao.exists(sala.getNumero())) {
                        if (dao.create(sala)) {
                            return sala;
                        } else {
                            Mensagem.aviso("Não foi possivel cadastrar a Sala.");
                        }
                    } else {
                        Mensagem.aviso("Já existe uma Sala cadastrada com este mesmo número.");
                    }
                } else {
                    Mensagem.aviso("A Sala deve ter uma Capacidade.");
                }
            } else {
                Mensagem.aviso("A sala deve ter um Numero.");
            }
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
    public Sala get(int id) {
        try {
            Sala sala = new SalaDAO().get(id);

            return sala;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }
    
    
    public int getCapacidade(int id) {
        try {
            int cap = new SalaDAO().getCapacidade(id);

            return cap;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return -1;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Sala> getAll() {
        try {
            ArrayList<Sala> salas = new SalaDAO().getAll();

            return salas;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @param sala
     * @return
     */
    public Sala update(Sala sala) {
        try {
           SalaDAO dao = new SalaDAO();
            
            if (sala.getNumero() > 0) {
                if (sala.getCapacidade() > 0) {
                    if (!dao.exists(sala.getNumero(), sala.getSalaID())) {
                        if (dao.update(sala)) {
                            return sala;
                        } else {
                            Mensagem.aviso("Não foi possivel atualizar a Sala.");
                        }
                    } else {
                        Mensagem.aviso("Já existe uma Sala cadastrada com este mesmo número.");
                    }
                } else {
                    Mensagem.aviso("A Sala deve ter uma Capacidade.");
                }
            } else {
                Mensagem.aviso("A sala deve ter um Numero.");
            }
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
                return new SalaDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return false;
    }

    /**
     *
     * @return
     */
    public String[] getAllMetaData() {
        try {
            return new SalaDAO().getAllMetaData();
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }
        return null;
    }
}
