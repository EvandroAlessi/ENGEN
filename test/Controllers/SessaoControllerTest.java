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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author SpaceBR
 */
public class SessaoControllerTest {
    Sessao sessao, sessaoTeste;
    SessaoController sessaoController = new SessaoController();
    FilmeController filmeController = new FilmeController();
    SalaController salaController = new SalaController();
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
        Filme filme = filmeController.get(1);
        Sala sala = salaController.get(1);
        // ============= MONTAGEM CENÁRIO ============= //
        this.sessao = new Sessao(1,filme.getFilmeID(),sala.getSalaID(),1,LocalDateTime.of(2019, Month.NOVEMBER, 11, 2, 23),10,filme,sala);
        // ================ EXECUÇÃO ================= //       
        this.sessaoTeste = sessaoController.create(sessao);
        // ============== VERIFICAÇÃO =============== // 
        assertNotNull(sessaoTeste);
    }
    
    @Test (expected = NoClassDefFoundError.class)
    public void createIngressosNullTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        Filme filme = filmeController.get(1);
        Sala sala = salaController.get(1);
        this.sessao = new Sessao(1,filme.getFilmeID(),sala.getSalaID(),0,LocalDateTime.of(2019, Month.NOVEMBER, 11, 2, 23),10,filme,sala);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.sessaoTeste = sessaoController.create(sessao);
            fail();
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
    
    @Test (expected = NoClassDefFoundError.class)
    public void createDataNullTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        Filme filme = filmeController.get(1);
        Sala sala = salaController.get(1);
        this.sessao = new Sessao(1,filme.getFilmeID(),sala.getSalaID(),1,null,10,filme,sala);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.sessaoTeste =sessaoController.create(sessao);
            fail();
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
    
    @Test (expected = ExceptionInInitializerError.class)
    public void createValorIngressosNullTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        Filme filme = filmeController.get(1);
        Sala sala = salaController.get(1);
        this.sessao = new Sessao(1,filme.getFilmeID(),sala.getSalaID(),1,LocalDateTime.of(2019, Month.NOVEMBER, 11, 2, 23),-1,filme,sala);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.sessaoTeste = sessaoController.create(sessao);
            assertTrue(false);
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
}
