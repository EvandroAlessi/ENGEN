/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Controllers.FilmeControllerTest;
import Models.Filme;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author a2058154
 */
public class FilmeDAOTest {
    Filme filme,filmeTeste;
    FilmeDAO filmeDAO = new FilmeDAO();
    
    @After
    public void tearDown(){
            try {
                if (filme != null) {
                    filmeDAO.delete(filme.getFilmeID());
                    this.filme = null;
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
        this.filme = new Filme("Coringa","Todd Phillips","Drama","PT-BR",122);
        
        // ================ EXECUÇÃO ================= //
        try {
            assertTrue(this.filmeDAO.create(filme)); // VERIFICAÇÃO
        } catch (SQLException ex) {
            Logger.getLogger(FilmeDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FilmeDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void createNullTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.filme = null;
        
        // ================ EXECUÇÃO ================= //
        try {
            boolean x = filmeDAO.create(filme); 
            fail();
        } catch (Exception ex){
            assertTrue(true);
        }
    }

}
