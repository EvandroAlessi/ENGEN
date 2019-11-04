/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import DAO.FilmeDAO;
import Models.Filme;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador de Filmes
 * Propicia a comunicação entre a GUI e DAO
 * Implementa a lógica de negócios
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Filme
 * @see FilmeDAO
 */
public class FilmeController {

    /**
     *
     * @param filme
     * @return
     */
    public Filme create(Filme filme) {
        try {
            FilmeDAO dao = new FilmeDAO();
            
            if (filme.getTitulo() != null && filme.getTitulo().length() > 0) {
                if (filme.getDiretor() != null && filme.getDiretor().length() > 0) {
                    if (filme.getGenero() != null && filme.getGenero().length() > 0) {
                        if (filme.getIdioma() != null && filme.getIdioma().length() > 0) {
                            if (filme.getDuracao() > 0) {
                                if (!dao.exists(filme.getTitulo())) {
                                    if (dao.create(filme)) {
                                        return filme;
                                    } else {
                                        Mensagem.aviso("Não foi possivel cadastrar o filme.");
                                    }
                                }
                                else{
                                    Mensagem.aviso("Já existe um Filme cadastrado com esse Titulo.");
                                }
                            }
                            else{
                                Mensagem.aviso("O filme deve ter uma Duração.");
                            }
                        } else {
                            Mensagem.aviso("O filme deve ter um Idioma.");
                        }
                    } else {
                        Mensagem.aviso("O filme deve ter um Gênero.");
                    }
                } else {
                    Mensagem.aviso("O filme deve ter um Diretor.");
                }
            } else {
                Mensagem.aviso("O filme deve ter um Título.");
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
    public Filme get(int id) {
        try {
            Filme filme = new FilmeDAO().get(id);

            return filme;
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
    public ArrayList<Filme> getAll() {
        try {
            ArrayList<Filme> filmes = new FilmeDAO().getAll();

            return filmes;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }
    
    /**
     *
     * @param diretor
     * @return
     */
    public ArrayList<Filme> getAll(String diretor) {
        try {
            ArrayList<Filme> filmes = new FilmeDAO().getAll(diretor);

            return filmes;
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
    public ArrayList<Filme> getAllInCartaz() {
        try {
            ArrayList<Filme> filmes = new FilmeDAO().getAllInCartaz();

            return filmes;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @param filme
     * @return
     */
    public Filme update(Filme filme) {
        try {
            FilmeDAO dao = new FilmeDAO();

             if (filme.getTitulo() != null && filme.getTitulo().length() > 0) {
                if (filme.getDiretor() != null && filme.getDiretor().length() > 0) {
                    if (filme.getGenero() != null && filme.getGenero().length() > 0) {
                        if (filme.getIdioma() != null && filme.getIdioma().length() > 0) {
                            if (filme.getDuracao() > 0) {
                                if (!dao.exists(filme.getTitulo(), filme.getFilmeID())) {
                                    if (dao.update(filme)) {
                                        return filme;
                                    } else {
                                        Mensagem.aviso("Não foi possivel atualizar o filme.");
                                    }
                                }
                                else{
                                    Mensagem.aviso("Já existe um Filme cadastrado com esse Titulo.");
                                }
                            }
                            else{
                                Mensagem.aviso("O filme deve ter uma Duração.");
                            }
                        } else {
                            Mensagem.aviso("O filme deve ter um Idioma.");
                        }
                    } else {
                        Mensagem.aviso("O filme deve ter um Gênero.");
                    }
                } else {
                    Mensagem.aviso("O filme deve ter um Diretor.");
                }
            } else {
                Mensagem.aviso("O filme deve ter um Título.");
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
                return new FilmeDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return false;
    }
}
