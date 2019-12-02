/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.ProdutoController;
import CrossCutting.DateTimePicker;
import CrossCutting.Mensagem;
import Models.Produto;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Diretamente ligada ao Stage provido pela CategoriaSubFX
 * Utiliza o controlador de Produto e Produto
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Produto
 * @see Produto
 * @see ProdutoController
 * @see ProdutoController
 * @see CategoriaSubFX
 */
public class CadastroProdutoFX {

    private final Label lbPrecoPontuacao, lbDescricao, lbPreco, lbTitle;
    private final TextField tfPrecoPontuacao, tfDescricao, tfPreco;
    private final Button btnCadastrar, btnCancelar;
    private ProdutoController produtoController;
    private List<Produto> produtos;
    private Produto produtoCriada;

    private Stage dialog;

    public CadastroProdutoFX() {
        produtoCriada = null;
        lbDescricao = new Label("Descricao:");
        lbPreco = new Label("Preco:"); 
        tfPrecoPontuacao = new TextField();
        tfDescricao = new TextField(); 
        tfPreco= new TextField();
        lbTitle = new Label("Nova Produto");
        lbPrecoPontuacao = new Label("Preco Pontuacao:");
        btnCadastrar = new Button("Cadastrar");
        btnCancelar = new Button("Cancelar");
        produtoController = new ProdutoController();
        produtos = produtoController.getAll();

        btnCadastrar.setOnAction(e -> {
            boolean ok = false;
            if (produtoCriada == null) {

                Produto nProduto = new Produto();

                try {
                    nProduto.setPrecoPontuacao(Integer.parseInt(tfPrecoPontuacao.getText()));
                    nProduto.setDescricao(tfDescricao.getText());
                    nProduto.setPreco(Double.parseDouble(tfPreco.getText()));
                    
                    produtoCriada = produtoController.create(nProduto);

                    if (produtoCriada != null) {
                        ok = true;
                    }
                } catch (Exception ex) {
                    Mensagem.informacao("Alguns dos valores informados está no formato incorreto!");
                }
            } else {
                try {
                    produtoCriada.setPrecoPontuacao(Integer.parseInt(tfPrecoPontuacao.getText()));
                    produtoCriada.setDescricao(tfDescricao.getText());
                    produtoCriada.setPreco(Double.parseDouble(tfPreco.getText()));

                    produtoCriada = produtoController.update(produtoCriada);
                    if (produtoCriada != null) {
                        ok = true;
                    }
                } catch (Exception ex) {
                    Mensagem.informacao("Alguns dos valores informados está no formato incorreto!");
                }
            }

            if (ok) {
                dialog.close();
            }
        });

        btnCancelar.setOnAction(e -> {
            dialog.close();
        });
    }

    /**
     *
     * @param mainStage
     * @param produto
     * @throws Exception
     */
    public void start(Stage mainStage, Produto produto) throws Exception {
        dialog = new Stage();
        GridPane painel = criarFormulario(produto);
        dialog.initOwner(mainStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(painel, 430, 380);
        
        dialog.setResizable(false);
        dialog.setMaxHeight(450);
        dialog.setMaxWidth(430);
        dialog.setMinHeight(450);
        dialog.setMinWidth(430);

        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private GridPane criarFormulario(Produto produtoCriada) {
        GridPane pane = new GridPane();
        HBox hbButtons = new HBox();
        
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(10);
        pane.setVgap(20);
        pane.setPadding(new Insets(25, 10, 25, 25));

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHalignment(HPos.CENTER);

        RowConstraints r1 = new RowConstraints();
        r1.setValignment(VPos.CENTER);

        pane.getColumnConstraints().add(c1);
        pane.getRowConstraints().add(r1);

        hbButtons.getChildren().addAll(btnCadastrar, btnCancelar);
        hbButtons.setSpacing(10);
        hbButtons.setPadding(new Insets(20, 0, -10, 0));
        hbButtons.setAlignment(Pos.CENTER);

        lbTitle.setPadding(new Insets(0, 0, 25, 0));
        lbTitle.setAlignment(Pos.CENTER);
        
        tfPrecoPontuacao.setPrefWidth(200);
        tfDescricao.setPrefWidth(200);
        tfDescricao.setPrefWidth(200);
        tfPreco.setPrefWidth(200);
        
        pane.add(lbTitle, 0, 0);
        
        pane.add(lbPrecoPontuacao, 0, 1);
        
        pane.add(lbDescricao, 0, 2);
        pane.add(tfPrecoPontuacao, 1, 1);
        pane.add(tfDescricao, 1, 2);
        
        pane.add(tfPreco, 1, 3);
        pane.add(lbPreco, 0, 3);
        
        pane.add(hbButtons, 0, 7, 4, 1);

        lbTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 23));
        lbDescricao.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbPrecoPontuacao.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        btnCadastrar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        btnCancelar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));

        if (produtoCriada != null) {
            this.produtoCriada = produtoCriada;
            this.lbTitle.setText("Editar Produto");
            btnCadastrar.setText("Salvar");
            
            this.tfPrecoPontuacao.setText(String.valueOf(produtoCriada.getPrecoPontuacao()));
            this.tfDescricao.setText(produtoCriada.getDescricao());
            this.tfPreco.setText(String.valueOf(produtoCriada.getPreco()));
        } 
        
        return pane;
    }

    public Produto getProdutoCriada() {
        return produtoCriada;
    }
}