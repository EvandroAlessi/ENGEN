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
       Filme filme = new Filme("Coringa2","Todd Phillips","Drama","PT-BR",122);
        
        // ================ EXECUÇÃO ================= //       
        this.filmeTeste = filmeController.create(filme);
        
        // ============== VERIFICAÇÃO =============== // 
        assertNotNull(filmeTeste);
    }
    
    
    @Test (expected = NoClassDefFoundError.class)
    public void createDiretorNullTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.filme = new Filme("Coringa","","Drama","PT-BR",122);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.filmeTeste = filmeController.create(filme);
            fail();
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
    
    @Test (expected = NoClassDefFoundError.class)
    public void createTituloNullTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        Filme filme = new Filme(null,"Todd Phillips","Drama","PT-BR",122);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.filmeTeste = filmeController.create(filme);
            fail();
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
    
    @Test (expected = NoClassDefFoundError.class)
    public void createGeneroNullTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        Filme filme = new Filme("Coringa","Todd Phillips",null,"PT-BR",122);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.filmeTeste = filmeController.create(filme);
            fail();
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
    
    @Test (expected = NoClassDefFoundError.class)
    public void createIdiomaTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        Filme filme = new Filme("Coringa","Todd Phillips","Drama",null,122);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.filmeTeste = filmeController.create(filme);
            fail();
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
    
    @Test (expected = NoClassDefFoundError.class)
    public void createDuracaoTest(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.filme = new Filme("Coringa","Todd Phillips","Drama","PT-BR",0);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.filmeTeste = filmeController.create(filme);
            fail();
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
    
    @Test (expected = ExceptionInInitializerError.class)
    public void createAlreadyExistsTitle(){
        // ============= MONTAGEM CENÁRIO ============= //
        this.filme = new Filme("Coringa","Todd Phillips","Drama","PT-BR",0);
        
        // ================ EXECUÇÃO ================= //
        try{
            this.filmeTeste = filmeController.create(filme);
            this.filmeTeste = filmeController.create(filme);
            fail();
        }
        catch(Exception ex){
            assertTrue(true);
        }
    }
}