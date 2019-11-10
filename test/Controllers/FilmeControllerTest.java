package Controllers;
import DAO.FilmeDAO;
import Models.Filme;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author SpaceBR
 */
public class FilmeControllerTest {
    FilmeController filmeController = new FilmeController();
    FilmeDAO filmeDAO = new FilmeDAO();
    Filme filme,filmeTeste;
    
    
    @After
    public void tearDown(){
        if(filmeTeste != null){
            try {
                filmeDAO.delete(filmeTeste.getFilmeID());
                this.filmeTeste = null;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FilmeControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(FilmeControllerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.filme = null;
    }
    
    @Test
    public void createTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.filme = new Filme("Coringa","Todd Phillips","Drama","PT-BR",122);
        
        // ================ EXECUÇÃO ================= //       
        this.filmeTeste = filmeController.create(filme);
        
        // ============== VERIFICAÇÃO =============== // 
        assertNotNull(filmeTeste);
    }
    
    @Test
    public void createTituloNullTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.filme = new Filme("aaa","","Drama","PT-BR",122);
        
        // ================ EXECUÇÃO ================= //       
        this.filmeTeste = filmeController.create(filme);
        
        // ============== VERIFICAÇÃO =============== // 
        assertNull(filmeTeste);
    }
}