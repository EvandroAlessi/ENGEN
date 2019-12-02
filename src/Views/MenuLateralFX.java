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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Window;

/**
 * View responsável pelo menu lateral. É ligada ao Stage principal gerado em MainFX.
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see MainFX
 */

public class MenuLateralFX extends GridPane {
    // Declaração de componentes
    GridPane menu;
    VBox vbTop;
    VBox vbBottom;
    VBox vbRight;
    Button btnCartaz, btnSell, btnSellProduct, btnSessao, btnFilme, btnSala, btnCliente, btnProduto, btnSair;
    Image grafico;
    ImageView imageView;
    
    /** @param main Recebe MainFX
    */
    public MenuLateralFX(MainFX main) {
        //Instanciação de componentes
        vbTop = new VBox();
        vbBottom = new VBox();
        btnCartaz = new Button("Em Cartaz");
        btnSell = new Button("Vender Ingresso");
        btnSellProduct = new Button("Vender Produtos");
        btnSessao = new Button("Sessões");
        btnFilme = new Button("Filmes");
        btnSala = new Button("Salas");
        btnCliente= new Button("Clientes");
        btnProduto = new Button("Produtos");
        btnSair = new Button("Sair");
        vbRight = new VBox();
        
        grafico = new Image(MenuLateralFX.class.getResourceAsStream("/Resources/icon-2.jpg"));
        imageView = new ImageView(grafico);
        
        btnSell.setOnAction((event) -> {
            main.switchCenter(Tela.VENDA_INGRESSO);
        });
        
        btnSellProduct.setOnAction((event) -> {
            main.switchCenter(Tela.VENDA_PRODUTO);
        });
        
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
            main.switchCenter(Tela.CLIENTE);
        });
        
        btnProduto.setOnAction((event) -> {
            main.switchCenter(Tela.PRODUTO);
        });
        
        // Evento para confirmação de saída
        btnSair.setOnAction((event) -> {
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
        
        //Definindo tamanhos
        imageView.setFitHeight(120);
        imageView.setFitWidth(120);
        btnSell.setMinWidth(150);
        btnSell.setMinHeight(60);
        btnSellProduct.setMinWidth(150);
        btnSellProduct.setMinHeight(60);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        btnCartaz.setPadding(new Insets(25));
        vbTop.getChildren().addAll(imageView, btnSell, btnSellProduct, btnCartaz, btnSessao, btnFilme, btnSala, btnCliente, btnProduto);
        vbBottom.getChildren().addAll(btnSair);
        btnCartaz.setMinWidth(150);
        btnCartaz.setMinHeight(60);
        btnFilme.setMinWidth(150);
        btnFilme.setMinHeight(60);
        btnSessao.setMinWidth(150);
        btnSessao.setMinHeight(60);
        btnSala.setMinWidth(150);
        btnSala.setMinHeight(60);
        btnCliente.setMinWidth(150);
        btnCliente.setMinHeight(60);
        btnProduto.setMinWidth(150);
        btnProduto.setMinHeight(60);
        btnSair.setMinWidth(150);
        btnSair.setMinHeight(60);
        
       btnSell.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold;"
                + "-fx-background-color: green;"
                + "-fx-color: white;"
                + "-fx-alignment: center;" 
                + "-fx-font-size: 17;");
       
       btnSellProduct.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-margin-top: 15;"
                + "-fx-font-weight: bold;"
                + "-fx-background-color: chartreuse;"
                + "-fx-color: white;"
                + "-fx-alignment: center;" 
                + "-fx-font-size: 17;");
        
        btnCartaz.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold;");
        
        
        btnFilme.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");
        
        btnSala.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");
        
        btnSessao.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");
        
        btnCliente.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");
        
        btnProduto.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold");
        
        btnSair.setStyle("-fx-background-insets: 0,0; "
                + "-fx-padding: 1; "
                + "-fx-border: 0;"
                + "-fx-font-weight: bold;"
                + "-fx-background-color: #d64646;"
                + "-fx-color: white;");
        
        btnSair.setTextFill(Color.WHITE);
        btnSell.setTextFill(Color.WHITE);
        btnSell.setTextAlignment(TextAlignment.CENTER);
        
        btnSellProduct.setTextFill(Color.WHITE);
        btnSellProduct.setTextAlignment(TextAlignment.CENTER);
        
        add(vbTop, 0, 0);
        add(vbBottom, 0, 1);
        add(vbRight, 1, 0);

        setRowSpan(vbRight, 2);
        vbRight.setStyle("-fx-background-color: #ddd");

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        c1.setPrefWidth(150);
        c1.setHalignment(HPos.CENTER);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setHgrow(Priority.NEVER);
        c2.setPrefWidth(1);
        c2.setHalignment(HPos.CENTER);
        vbTop.setAlignment(Pos.TOP_CENTER);
        vbBottom.setAlignment(Pos.BOTTOM_CENTER);

        RowConstraints r1 = new RowConstraints();
        r1.setVgrow(Priority.ALWAYS);
        r1.setValignment(VPos.CENTER);
        RowConstraints r2 = new RowConstraints();
        r2.setVgrow(Priority.ALWAYS);
        r2.setPrefHeight(120);
        r2.setMaxHeight(120);
        r2.setMinHeight(120);
        r2.setValignment(VPos.CENTER);

        getColumnConstraints().add(c1);
        getRowConstraints().add(r1);
        getRowConstraints().add(r2);
        getColumnConstraints().add(c2);
    }
}
