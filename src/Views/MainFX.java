/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import CrossCutting.Log;
import CrossCutting.Mensagem;
import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * View principal, onde o programa é inicializado e a primeira janela é criada
 * @author Evandro Alessi
 * @author Eric Ueta
 */
public class MainFX extends Application {

    // Declaração de containers e componentes
    private static BorderPane root;
    private MenuSuperiorFX menuSuperior;
    private MenuLateralFX menuLateral;
    //private ResumoFX resumo;
    private Stage stage;

    @Override
    public void init() {
        // Instanciação da tela inicial e menus
        root = new BorderPane();
        menuSuperior = new MenuSuperiorFX(this);
        menuLateral = new MenuLateralFX(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        root.setLeft(menuLateral);
        root.setTop(menuSuperior);
        root.setStyle("-fx-focus-color: transparent;"
                + "-fx-faint-focus-color: #d2d4d6;");
        switchCenter(Tela.CARTAZ);
        primaryStage.setMaximized(true); // Tela cheia
        Scene scene = new Scene(root, 700, 700);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        primaryStage.setTitle("Cinema");
        primaryStage.setScene(scene);
        
        stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e-> {
            e.consume();
            ButtonType btnSim = new ButtonType("Sim", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING, "", btnSim, btnNao);
            alert.setHeaderText("Deseja realmente sair?");
            alert.setContentText("Tem certeza?");
            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(ev -> alert.hide());
            Optional<ButtonType> result = alert.showAndWait();
            result.ifPresent(res->{
                if (res.equals(btnSim)) {
                    Platform.exit();
                    System.exit(0);
                } else if (res.equals(btnNao)) {
                    alert.close();
                }
            });
        });
        
        primaryStage.show();
    }

    /**
     * Alternar entre as telas exibidas
     * @param tela
     */
    public void switchCenter(Tela tela) {
        switch (tela) {
            case SALA:
                root.setCenter(new SalaFX(this.stage));
                break;
            case FILME:
                root.setCenter(new FilmeFX(this.stage));
                break;
            case SESSAO:
                root.setCenter(new SessaoFX(this.stage));
                break;
            case CARTAZ:
                root.setCenter(new CartazFX(this.stage));
                break;
            case CLIENTE:
                root.setCenter(new ClienteFX(this.stage));
                break;
            case PRODUTO:
                root.setCenter(new ProdutoFX(this.stage));
                break;
            case VENDA_INGRESSO:
                VendaIngressoFX form = new VendaIngressoFX();
                try {
                    form.start(this.stage);
                } catch (Exception ex) {
                    Log.saveLog(ex);
                    Mensagem.excecao(ex);
                }
                break;
            case VENDA_PRODUTO:
                VendaProdutoFX form2 = new VendaProdutoFX();
                try {
                    form2.start(this.stage);
                } catch (Exception ex) {
                    Log.saveLog(ex);
                    Mensagem.excecao(ex);
                }
                break;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
