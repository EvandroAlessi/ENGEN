package Controllers;

import DAO.SalaDAO;
import Models.Sala;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.assertNotNull;

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
}
