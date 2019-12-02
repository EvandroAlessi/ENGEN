/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import CrossCutting.Enums.Tela;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;

/**
 * View responsável pelo menu superior. É ligada ao Stage principal gerado em MainFX.
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see MainFX
 */
public class MenuSuperiorFX extends MenuBar {
    // Declaração de componentes
    MenuBar menuSuperior;
    Menu arquivo, geral;
    MenuItem sair, btnCartaz, btnSessao, btnFilme, btnSala, btnCliente, btnProduto;

    /** @param main Recebe MainFX
    */
    public MenuSuperiorFX(MainFX main) {
        //Instanciação de componentes
        arquivo = new Menu("Arquivo");
        geral = new Menu("Geral");
        btnCartaz = new MenuItem("Em Cartaz");
        btnSessao = new MenuItem("Sessões");
        btnFilme = new MenuItem("Filmes");
        btnSala = new MenuItem("Salas");
        btnCliente = new MenuItem("Clientes");
        btnProduto = new MenuItem("Produtos");
        sair = new MenuItem("Sair      ");
        arquivo.getItems().add(sair);
        
        geral.getItems().addAll(btnCartaz, btnSessao, btnFilme, btnSala, btnCliente, btnProduto);
        
        getMenus().addAll(arquivo, geral);

        btnCartaz.setOnAction((event) -> {
            main.switchCenter(Tela.CARTAZ);
        });
        
        btnSessao.setOnAction((event) -> {
            main.switchCenter(Tela.SESSAO);
        });

        btnFilme.setOnAction((event) -> {
            main.switchCenter(Tela.FILME);
        });

        btnSala.setOnAction((event) -> {
            main.switchCenter(Tela.SALA);
        });
        
        btnCliente.setOnAction((event) -> {
            main.switchCenter(Tela.SALA);
        });
        
        btnProduto.setOnAction((event) -> {
            main.switchCenter(Tela.PRODUTO);
        });

        // Evento para confirmação de saída
        sair.setOnAction((event) -> {
            ButtonType btnSim = new ButtonType("Sim", ButtonBar.ButtonData.OK_DONE);
            ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING, "", btnSim, btnNao);
            alert.setHeaderText("Deseja realmente sair?");
            alert.setContentText("Tem certeza?");
            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(e -> alert.hide());
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
    }
}
