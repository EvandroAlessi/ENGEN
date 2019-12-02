/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import DAO.SalaDAO;
import DAO.SessaoDAO;
import Models.Sessao;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador de Categorias
 * Propicia a comunicação entre a GUI e DAO
 * Implementa a lógica de negócios
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Sessao
 * @see SessaoDAO
 */
public class SessaoController {

    /**
     *
     * @param sessao
     * @return
     */
    public Sessao create(Sessao sessao) {
        try {
            SessaoDAO dao = new SessaoDAO();
            SalaDAO salaDAO = new SalaDAO();
            
            if (sessao.getIngressos() > 0) {
                if (sessao.getData() != null) {
                    if (sessao.getValorIngresso() >= 0) {
                        if (!dao.exists(sessao.getSalaID(), sessao.getData())) {
                            if (dao.create(sessao)) {
                                return sessao;
                            } else {
                                Mensagem.aviso("Não foi possivel abrir a sessão.");
                            }
                        } else {
                            Mensagem.aviso("Já existe uma sessão para esta sala.");
                        }
                    }else{
                        Mensagem.aviso("O valor do Ingresso não pode ser negativo ou nulo.");
                    }
                }else{
                    Mensagem.aviso("A data deve ser preenchida.");
                }
            } else {
                Mensagem.aviso("A sala está com a capacidade incorreta.");
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
    public Sessao get(int id) {
        try {
            if (id != 0) {
                Sessao sessao = new SessaoDAO().get(id);
                return sessao;
            }
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
    public ArrayList<Sessao> getAll() {
        try {
            ArrayList<Sessao> sessoes = new SessaoDAO().getAll();

            return sessoes;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }
    
    /**
     *
     * @param sessao
     * @return
     */
    public Sessao update(Sessao sessao) {
        try {
            SessaoDAO dao = new SessaoDAO();

            SalaDAO salaDAO = new SalaDAO();
            
            if (salaDAO.getCapacidade(sessao.getSalaID()) > 0) {
                if (sessao.getData() != null) {
                    if (sessao.getValorIngresso() >= 0) {
                        if (!dao.exists(sessao.getSalaID(), sessao.getData())) {
                            if (dao.update(sessao)) {
                                return sessao;
                            } else {
                                Mensagem.aviso("Não foi possivel abrir a sessão.");
                            }
                        } else {
                            Mensagem.aviso("Já existe uma sessão para esta sala.");
                        }
                    }else{
                        Mensagem.aviso("O valor do Ingresso não pode ser negativo ou nulo.");
                    }
                }else{
                    Mensagem.aviso("A data deve ser preenchida.");
                }
            } else {
                Mensagem.aviso("A sala está com a capacidade incorreta.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }
    
     /**
     *
     * @param sessao
     * @return
     */
    public Sessao updateIngressos(Sessao sessao) {
        try {
            SessaoDAO dao = new SessaoDAO();
            
            if (sessao.getIngressos() >= 0) {
                if (dao.updateIngressos(sessao)) {
                    return sessao;
                } else {
                    Mensagem.aviso("Não foi possivel abrir a sessão.");
                }
            }else{
                Mensagem.aviso("Todos ingressos já vendidos, ecolha outra sessão.");
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
                return new SessaoDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return false;
    }
}
