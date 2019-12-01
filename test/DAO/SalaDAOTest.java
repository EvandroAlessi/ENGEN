/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Controllers.FilmeControllerTest;
import Models.Filme;
import Models.Sala;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author a2058154
 */
public class SalaDAOTest {
    SalaDAO salaDAO = new SalaDAO();
    Sala sala, salaTeste;
    
    
    @After
    public void tearDown(){
            try {
                if (sala != null) {
                    salaDAO.delete(this.sala.getSalaID());
                    this.sala = null;
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FilmeControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(FilmeControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test
    public void createTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.sala = new Sala(55,50,50);
        
        // ================ EXECUÇÃO ================= //
        try {
            assertTrue(this.salaDAO.create(sala)); // VERIFICAÇÃO
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilmeDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void createNullTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.sala = null;
        
        // ================ EXECUÇÃO ================= //
        try {
            boolean x = salaDAO.create(sala); 
            fail();
        } catch (Exception ex){
            assertTrue(true);
        }
    }
}
