package Controllers;

import DAO.SalaDAO;
import Models.Filme;
import Models.Sala;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 * @author SpaceBR
 */
public class SalaControllerTest {
    Sala sala, salaTeste;
    SalaController salaController = new SalaController();
    SalaDAO salaDAO = new SalaDAO();
    
    @After
    public void tearDown(){
        if(salaTeste != null){
            try {
                salaDAO.delete(salaTeste.getSalaID());
                this.salaTeste = null;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FilmeControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(FilmeControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.sala = null;
    }
    
    @Test
    public void createTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.sala = new Sala(55,50,50);
        // ================ EXECUÇÃO ================= //       
        this.salaTeste = salaController.create(sala);
        
        // ============== VERIFICAÇÃO =============== // 
        assertNotNull(salaTeste);
    }
    
    @Test (expected = NoClassDefFoundError.class)
    public void createNumeroNullTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.sala = new Sala(55,0,55);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.salaTeste =salaController.create(sala);
            fail();
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
    
    @Test (expected = NoClassDefFoundError.class)
    public void createCapacidadeNullTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.sala = new Sala(55,55,0);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.salaTeste =salaController.create(sala);
            fail();
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
    
    @Test (expected = ExceptionInInitializerError.class)
    public void createAlreadyExistsNumber(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.sala = new Sala(55,55,0);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.salaTeste =salaController.create(sala);
            this.salaTeste =salaController.create(sala);
            fail();
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
}
