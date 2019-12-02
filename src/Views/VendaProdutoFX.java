/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.ProdutoController;
import Controllers.ClienteController;
import Controllers.ProdutoController;
import CrossCutting.DateTimePicker;
import CrossCutting.Mensagem;
import Models.Produto;
import Models.Cliente;
import Models.Filme;
import Models.Sala;
import Models.Produto;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * View com formulário de cadastro para Despesas.
 * Diretamente ligada ao Stage provido pela DespesaFX
 * Utiliza o controlador de CategoriaConta, SubCategoria e Despesa
 * @author Evandro Alessi
 * @author Eric Ueta
 */
public class VendaProdutoFX {

    private final Label lbCliente, lbValor, lbValorPoint, lbProduto, lbQuantia, lbTitle, lbTotal, lbTipoPagamento;
    private final TextField tfValorPoint, tfValor, tfQuantia, tfTotal, tfTotalPoints;
    private final Button btnAdd, btnCancelar, btnFinalizar;
    private TextArea listaCompras;
    private Stage dialog;
    private ProdutoController produtoController;
    private final List<Cliente> clientes;
    private final List<Produto> produtos;
    private List<String> tiposPagamento;
    private ComboBox<Cliente> cbCliente;
    private ComboBox<Produto> cbProduto;
    private ComboBox<String> cbTipoPagamento;
    private ClienteController clienteController;
    
    public VendaProdutoFX() {
        listaCompras = new TextArea();
        lbProduto = new Label("Produto:");
        lbQuantia = new Label("Quantidade Desejada:");
        lbTipoPagamento = new Label("Tipo de Pagamento:");
        tfValorPoint = new TextField();
        tfValor = new TextField();
        tfQuantia = new TextField();
        lbValor = new Label("Preço:");
        lbValorPoint = new Label("Preço usando Pontos:");
        lbTitle = new Label("Vendendo Ingresso");
        lbCliente = new Label("Cliente:");
        btnAdd = new Button("Adicionar Produto");
        btnCancelar = new Button("Cancelar");
        produtoController = new ProdutoController();
        clienteController = new ClienteController();
        clientes = clienteController.getAll();
        produtos = produtoController.getAll();
        cbProduto = new ComboBox();
        cbCliente = new ComboBox();
        cbTipoPagamento = new ComboBox();
        tfTotal = new TextField();
        tfTotalPoints = new TextField();
        lbTotal = new Label("TOTAL: ");
        btnFinalizar = new Button("Finalizar");
        tiposPagamento = new ArrayList<String>();
        tiposPagamento.add("Dinheiro");
        tiposPagamento.add("Pontuação");
        
        btnAdd.setOnAction(e -> {
            try {
                if (tfQuantia.getText() != null && tfQuantia.getText().length() > 0) {
                    if (Integer.parseInt(tfQuantia.getText()) > 0) {
                        listaCompras.setText(listaCompras.getText() + tfQuantia.getText() + " - " +  cbProduto.getSelectionModel().getSelectedItem().getDescricao() + "\n");
                        tfTotal.setText(String.valueOf(Double.parseDouble(tfTotal.getText()) + (Integer.parseInt(tfQuantia.getText()) * cbProduto.getSelectionModel().getSelectedItem().getPreco())));
                        tfTotalPoints.setText(String.valueOf(Double.parseDouble(tfTotalPoints.getText()) + (Integer.parseInt(tfQuantia.getText()) * cbProduto.getSelectionModel().getSelectedItem().getPrecoPontuacao())));
                    }
                    else{
                        Mensagem.aviso("Quantia igual a 0, não informada ou negativa!");
                    }
                }
                else{
                    Mensagem.aviso("Indique a quantia, por favor.");
                }
            } catch (NumberFormatException ex) {
                Mensagem.erro("Erro ao adicionar item!");
            }
        });
        
        btnFinalizar.setOnAction((event) -> {
            try {
                if (cbTipoPagamento.getSelectionModel().isSelected(0)) {
                    if (Double.parseDouble(tfTotal.getText()) > 0.0) {
                        Mensagem.informacao("Venda realizada com sucesso!");
                        dialog.close();
                    }
                    else{
                        Mensagem.informacao("Nada Adicionado a lista!");
                    }
                }
                else{
                    if (Double.parseDouble(tfTotalPoints.getText()) > 0.0) {
                        clienteController.removePoints(cbCliente.getSelectionModel().getSelectedItem(), (int)(Double.parseDouble(tfTotalPoints.getText())));
                        Mensagem.informacao("Venda realizada com sucesso!");
                        dialog.close();
                    }
                    else{
                        Mensagem.informacao("Nada Adicionado a lista!");
                    }
                }
            } catch (Exception e) {
                Mensagem.erro("Não foi possivel finalizar, sentimos muito pelo ocorrido, tente novamente!");
            }
        });

        btnCancelar.setOnAction(e -> {
            dialog.close();
        });
    }

    public void start(Stage mainStage) throws Exception {
        dialog = new Stage();
        GridPane painel = criarFormulario();
        dialog.initOwner(mainStage);
        dialog.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(painel, 400, 420);

        dialog.setResizable(false);
        dialog.setMaxHeight(600);
        dialog.setMaxWidth(600);
        dialog.setMinHeight(600);
        dialog.setMinWidth(600);

        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private GridPane criarFormulario() {
        GridPane pane = new GridPane();
        HBox l1 = new HBox();

        ListIterator<Produto> subIte = produtos.listIterator();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 10, 25, 25));

        cbCliente.setItems(FXCollections.observableArrayList(clientes));
        
        cbTipoPagamento.setItems(FXCollections.observableArrayList(tiposPagamento));
        
        cbProduto.setItems(FXCollections.observableArrayList(produtos));
        
        cbProduto.getSelectionModel().selectFirst();
        
        cbProduto.getSelectionModel().selectedItemProperty().addListener((opcoes, valorAntigo, valorNovo) -> {
            this.tfValorPoint.setText(String.valueOf(cbProduto.getSelectionModel().getSelectedItem().getPrecoPontuacao()));
            this.tfValor.setText(String.valueOf(cbProduto.getSelectionModel().getSelectedItem().getPreco()));
        });
        
        cbTipoPagamento.getSelectionModel().selectedItemProperty().addListener((opcoes, valorAntigo, valorNovo) -> {
            if (cbTipoPagamento.getSelectionModel().isSelected(0)) {
                this.lbTotal.setText("TOTAL: ");
                this.tfTotalPoints.setVisible(false);
                this.tfTotal.setVisible(true);
            }
            else{
                this.lbTotal.setText("TOTAL EM PONTOS: ");
                this.tfTotalPoints.setVisible(true);
                this.tfTotal.setVisible(false);
            }
        });
            
            
        
        cbCliente.getSelectionModel().selectedItemProperty().addListener((opcoes, valorAntigo, valorNovo) -> {
            if (!cbCliente.getSelectionModel().isEmpty()) {
                 this.cbTipoPagamento.setDisable(false);
            }
            else{
                this.cbTipoPagamento.setDisable(true);
            }
        });
        
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHalignment(HPos.CENTER);

        RowConstraints r1 = new RowConstraints();
        r1.setValignment(VPos.CENTER);

        pane.getColumnConstraints().add(c1);
        pane.getRowConstraints().add(r1);

        cbTipoPagamento.getSelectionModel().selectFirst();

        l1.getChildren().addAll(btnFinalizar, btnCancelar);
        l1.setSpacing(10);
        l1.setPadding(new Insets(35, 0, -10, 0));
        l1.setAlignment(Pos.CENTER);

        lbTitle.setPadding(new Insets(0, 0, 25, 0));
        lbTitle.setAlignment(Pos.CENTER);
        
        cbProduto.getSelectionModel().selectFirst();

        cbCliente.setPrefWidth(200);
        cbTipoPagamento.setPrefWidth(200);
        cbProduto.setPrefWidth(200);
        tfValorPoint.setPrefWidth(200);
        tfQuantia.setPrefWidth(200);
        listaCompras.setPrefWidth(200);
        listaCompras.setPrefHeight(200);

        tfValorPoint.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {

                try {
                    Double.parseDouble(tfValorPoint.getText());
                } catch (Exception e) {
                    tfValorPoint.setText(tfValorPoint.getText().substring(0, tfValorPoint.getText().length() - 1));
                }
                finally{
                    if (tfValorPoint.getText().length() > 7) {
                        String s = tfValorPoint.getText().substring(0, 7);
                        tfValorPoint.setText(s);
                    }
                }   
            }
        });
        
        pane.add(lbTitle, 0, 0, 4, 1);
        pane.add(cbCliente, 1, 1);
        pane.add(lbCliente, 0, 1);
        pane.add(cbProduto, 1, 2);
        pane.add(lbProduto, 0, 2);
        pane.add(lbValorPoint, 0, 3);
        pane.add(tfValorPoint, 1, 3);
        pane.add(lbValor, 0, 4);
        pane.add(tfValor, 1, 4);
        pane.add(lbQuantia, 0, 5);
        pane.add(tfQuantia, 1, 5);
        pane.add(listaCompras, 0, 7, 2, 11);
        pane.add(lbTipoPagamento, 0, 18);
        pane.add(cbTipoPagamento, 1, 18);
        pane.add(lbTotal, 0, 19);
        pane.add(tfTotal, 1, 19);
        pane.add(tfTotalPoints, 1, 19);
        pane.add(btnAdd, 0, 6, 2, 1);
        pane.add(l1, 0, 20, 2, 1);

        lbTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 23));
        lbProduto.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbTotal.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbQuantia.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbTipoPagamento.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbValor.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbValorPoint.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbCliente.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        btnAdd.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        btnCancelar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        btnFinalizar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
            
        this.tfValor.setText(String.valueOf(cbProduto.getSelectionModel().getSelectedItem().getPreco()));
        this.tfValorPoint.setText(String.valueOf(cbProduto.getSelectionModel().getSelectedItem().getPrecoPontuacao()));
        this.tfTotal.setText("0.0");
        this.tfTotalPoints.setText("0.0");     
        
        this.tfValor.setEditable(false);
        this.tfValorPoint.setEditable(false);
        this.tfTotal.setEditable(false);
        this.listaCompras.setEditable(false);
        this.cbTipoPagamento.setDisable(true);
        this.tfTotalPoints.setEditable(false);
        this.tfTotalPoints.setVisible(false);
        
        return pane;
    }
}
