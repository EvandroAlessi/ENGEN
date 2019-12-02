/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import DAO.ClienteDAO;
import Models.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador de Clientes
 * Propicia a comunicação entre a GUI e DAO
 * Implementa a lógica de negócios
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Cliente
 * @see ClienteDAO
 */
public class ClienteController {

    /**
     *
     * @param cliente
     * @return
     */
    public Cliente create(Cliente cliente) {
        try {
            ClienteDAO dao = new ClienteDAO();
            
            if (cliente.getCPF() != null && cliente.getCPF().length() > 0) {
                if (cliente.getNome() != null && cliente.getNome().length() > 0) {
                    if (cliente.getDataNascimento() != null) {
                        if (cliente.getPontuacao() >= 0) {
                            if (!dao.exists(cliente.getCPF())) {
                                if (dao.create(cliente)) {
                                    return cliente;
                                } else {
                                    Mensagem.aviso("Não foi possivel cadastrar o cliente.");
                                }
                            }
                            else{
                                Mensagem.aviso("Já existe um Cliente cadastrado com esse CPF.");
                            }
                        }
                        else{
                            Mensagem.aviso("Não é possível registrar uma pontuação negativa.");
                        }
                    } else {
                        Mensagem.aviso("O cliente deve ter uma Data de Nascimento.");
                    }
                } else {
                    Mensagem.aviso("O cliente deve ter um Nome.");
                }
            } else {
                Mensagem.aviso("O cliente deve ter um CPF.");
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
    public Cliente get(int id) {
        try {
            Cliente cliente = new ClienteDAO().get(id);

            return cliente;
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
    public ArrayList<Cliente> getAll() {
        try {
            ArrayList<Cliente> clientes = new ClienteDAO().getAll();

            return clientes;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }
    
    
    /**
     *
     * @param cliente
     * @return
     */
    public Cliente update(Cliente cliente) {
        try {
            ClienteDAO dao = new ClienteDAO();

             if (cliente.getCPF() != null && cliente.getCPF().length() > 0) {
                if (cliente.getNome() != null && cliente.getNome().length() > 0) {
                    if (cliente.getDataNascimento() != null) {
                        if (cliente.getPontuacao() >= 0) {
                            if (!dao.exists(cliente.getCPF(), cliente.getClienteID())) {
                                if (dao.update(cliente)) {
                                    return cliente;
                                } else {
                                    Mensagem.aviso("Não foi possivel atualizar o cliente.");
                                }
                            }
                            else{
                                Mensagem.aviso("Já existe um Cliente cadastrado com esse CPF.");
                            }
                        }
                        else{
                            Mensagem.aviso("Não é possível registrar uma pontuação negativa.");
                        }
                    } else {
                        Mensagem.aviso("O cliente deve ter uma Data de Nascimento.");
                    }
                } else {
                    Mensagem.aviso("O cliente deve ter um Nome.");
                }
            } else {
                Mensagem.aviso("O cliente deve ter um CPF.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    public Cliente addPoints(Cliente cliente, int points){
        try {
            ClienteDAO dao = new ClienteDAO();

            if (points > 0) {
                if (dao.addPoints(cliente.getClienteID(), points)) {
                    return cliente;
                } else {
                    Mensagem.aviso("Não foi possivel atualizar o cliente.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }
    
    public Cliente removePoints(Cliente cliente, int points){
        try {
            ClienteDAO dao = new ClienteDAO();

            if (cliente.getPontuacao() >= points) {
                if (dao.removePoints(cliente.getClienteID(), points)) {
                    return cliente;
                } else {
                    Mensagem.aviso("Não foi possivel atualizar o cliente.");
                }
            }
            else{
                Mensagem.aviso("Você não tem a pontuação necessária\n Sua pontuação é: " + cliente.getPontuacao());
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
                return new ClienteDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return false;
    }
}
