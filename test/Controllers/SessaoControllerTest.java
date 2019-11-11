/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.FilmeDAO;
import DAO.SalaDAO;
import DAO.SessaoDAO;
import Models.Filme;
import Models.Sala;
import Models.Sessao;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author SpaceBR
 */
public class SessaoControllerTest {
    Sessao sessao, sessaoTeste;
    SessaoController sessaoController = new SessaoController();
    FilmeDAO filmeDAO = new FilmeDAO();
    SalaDAO salaDAO = new SalaDAO();
    SessaoDAO sessaoDAO = new SessaoDAO();
    Sala sala;
    Filme filme;
    
    @After
    public void tearDown(){
        if(sessaoTeste != null){
            try {
                sessaoDAO.delete(sessaoTeste.getSessaoID());
                this.sessaoTeste = null;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FilmeControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(FilmeControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(sala != null){
            try {
                salaDAO.delete(sala.getSalaID());
                this.sala = null;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SessaoControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SessaoControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(filme != null){
            try {
                filmeDAO.delete(filme.getFilmeID());
                filme = null;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SessaoControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SessaoControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.sessao = null;
    }
    
    @Test
    public void createTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.filme = new Filme("Coringa","Todd Phillips","Drama","PT-BR",122);
        this.sala = new Sala(55,50,50);
        this.sessao = new Sessao(55,55,55,55,LocalDateTime.of(2019, Month.NOVEMBER, 11, 2, 23),10,filme,sala);
        // ================ EXECUÇÃO ================= //       
        this.sessaoTeste = sessaoController.create(sessao);
        // ============== VERIFICAÇÃO =============== // 
        assertNotNull(sessaoTeste);
    }
}
