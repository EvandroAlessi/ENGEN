/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.SessaoController;
import Controllers.ClienteController;
import Controllers.SessaoController;
import CrossCutting.DateTimePicker;
import CrossCutting.Mensagem;
import Models.Sessao;
import Models.Cliente;
import Models.Filme;
import Models.Sala;
import Models.Sessao;
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
 * @see CategoriaConta
 * @see SubCategoria
 * @see Despesa
 * @see DespesaController
 * @see CategoriaContaController
 * @see SubCategoriaController
 * @see DespesaFX
 */
public class VendaIngressoFX {

    private final Label lbData, lbCliente, lbSessao, lbValorIngresso, lbIngresso, lbQuantia, lbMeiaIntera, lbTitle;
    private final TextField tfValorIngresso, tfIngresso, tfQuantia, tfDataHora;
    private final Button btnVender, btnCancelar;
    private Stage dialog;
    private SessaoController sessaoController;
    private final List<Cliente> clientes;
    private final List<Sessao> sessoes;
    private List<String> meiaInteria;
    private ComboBox<Cliente> cbCliente;
    private ComboBox<String> cbMeia;
    private ComboBox<Sessao> cbSessao;
    private ClienteController clienteController;
    
    public VendaIngressoFX() {
        lbSessao = new Label("Sessao:");
        lbQuantia = new Label("Quantidade Desejada:");
        tfValorIngresso = new TextField();
        tfIngresso = new TextField();
        tfQuantia = new TextField();
        lbTitle = new Label("Vendendo Ingresso");
        lbData = new Label("Data:");
        lbCliente = new Label("Cliente:");
        lbValorIngresso = new Label("Valor:");
        lbIngresso = new Label("Ingressos Disponiveis:");
        btnVender = new Button("Vender");
        btnCancelar = new Button("Cancelar");
        tfDataHora = new TextField();
        sessaoController = new SessaoController();
        clienteController = new ClienteController();
        clientes = clienteController.getAll();
        sessoes = sessaoController.getAll();
        cbSessao = new ComboBox();
        cbCliente = new ComboBox();
        cbMeia = new ComboBox();
        lbMeiaIntera = new Label("Tipo:");
        meiaInteria = new ArrayList<>();
        meiaInteria.add("Interia");
        meiaInteria.add("Meia");
        
        btnVender.setOnAction(e -> {
            try {
                if (Integer.parseInt(tfQuantia.getText()) > 0 &&    cbSessao.getSelectionModel().getSelectedItem().getIngressos() >= Integer.parseInt(tfQuantia.getText())) {
                    if (!cbCliente.getSelectionModel().isEmpty()) {
                        clienteController.addPoints(cbCliente.getSelectionModel().getSelectedItem(),(int)(Double.parseDouble(tfValorIngresso.getText()) * Integer.parseInt(tfQuantia.getText())));
                    }

                    if (cbSessao.getSelectionModel().getSelectedItem().getIngressos() >= Integer.parseInt(tfQuantia.getText())) {
                        if (cbMeia.getSelectionModel().isSelected(0))
                            cbSessao.getSelectionModel().getSelectedItem().setValorIngresso(cbSessao.getSelectionModel().getSelectedItem().getValorIngresso() / 2);
                        
                        cbSessao.getSelectionModel().getSelectedItem().setIngressos(cbSessao.getSelectionModel().getSelectedItem().getIngressos() - Integer.parseInt(tfQuantia.getText()));
                        sessaoController.updateIngressos(cbSessao.getSelectionModel().getSelectedItem());
                    }
                    
                    Mensagem.informacao("Venda realizada com sucesso!");
                    dialog.close();
                }
                else{
                    Mensagem.aviso("Numero de ingressos desejados é inválido ou é maior que a quantidade disponivel!");
                }
            } catch (NumberFormatException ex) {
                Mensagem.erro("erro ao realizar venda, verifique os dados!");
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
        dialog.setMaxHeight(450);
        dialog.setMaxWidth(450);
        dialog.setMinHeight(450);
        dialog.setMinWidth(450);

        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private GridPane criarFormulario() {
        GridPane pane = new GridPane();
        HBox l1 = new HBox();

        ListIterator<Sessao> subIte = sessoes.listIterator();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 10, 25, 25));

        cbCliente.setItems(FXCollections.observableArrayList(clientes));
        
        cbMeia.setItems(FXCollections.observableArrayList(meiaInteria));
        
        cbSessao.setItems(FXCollections.observableArrayList(sessoes));
        
        cbMeia.getSelectionModel().selectFirst();
        
        cbSessao.getSelectionModel().selectedItemProperty().addListener((opcoes, valorAntigo, valorNovo) -> {
            this.tfDataHora.setText(cbSessao.getSelectionModel().getSelectedItem().getData().toString().replace("T", " Hora: "));
            this.tfValorIngresso.setText(String.valueOf(cbSessao.getSelectionModel().getSelectedItem().getValorIngresso()));
            this.tfIngresso.setText(String.valueOf(cbSessao.getSelectionModel().getSelectedItem().getIngressos()));
        });
        
        cbMeia.getSelectionModel().selectedItemProperty().addListener((opcoes, valorAntigo, valorNovo) -> {
            if (cbMeia.getSelectionModel().isSelected(1)) {
                this.tfValorIngresso.setText(String.valueOf(cbSessao.getSelectionModel().getSelectedItem().getValorIngresso() / 2));
            }
            else{
                this.tfValorIngresso.setText(String.valueOf(cbSessao.getSelectionModel().getSelectedItem().getValorIngresso()));
            }
        });
        
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHalignment(HPos.CENTER);

        RowConstraints r1 = new RowConstraints();
        r1.setValignment(VPos.CENTER);

        pane.getColumnConstraints().add(c1);
        pane.getRowConstraints().add(r1);

        //cbCliente.getSelectionModel().selectFirst();

        l1.getChildren().addAll(btnVender, btnCancelar);
        l1.setSpacing(10);
        l1.setPadding(new Insets(35, 0, -10, 0));
        l1.setAlignment(Pos.CENTER);

        lbTitle.setPadding(new Insets(0, 0, 25, 0));
        lbTitle.setAlignment(Pos.CENTER);

        cbSessao.getSelectionModel().selectFirst();

        cbCliente.setPrefWidth(200);
        cbMeia.setPrefWidth(200);
        cbSessao.setPrefWidth(200);
        tfValorIngresso.setPrefWidth(200);
        tfDataHora.setPrefWidth(200);
        tfQuantia.setPrefWidth(200);

        tfValorIngresso.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {

                try {
                    Double.parseDouble(tfValorIngresso.getText());
                } catch (Exception e) {
                    tfValorIngresso.setText(tfValorIngresso.getText().substring(0, tfValorIngresso.getText().length() - 1));
                }
                finally{
                    if (tfValorIngresso.getText().length() > 7) {
                        String s = tfValorIngresso.getText().substring(0, 7);
                        tfValorIngresso.setText(s);
                    }
                }   
            }
        });
        
        
        
        pane.add(lbTitle, 0, 0, 4, 1);
        pane.add(cbCliente, 1, 1);
        pane.add(lbCliente, 0, 1);
        pane.add(cbSessao, 1, 2);
        pane.add(lbSessao, 0, 2);
        pane.add(lbValorIngresso, 0, 3);
        pane.add(tfValorIngresso, 1, 3);
        pane.add(lbData, 0, 4);
        pane.add(tfDataHora, 1, 4);
        pane.add(lbIngresso, 0, 5);
        pane.add(tfIngresso, 1, 5);
        pane.add(lbQuantia, 0, 6);
        pane.add(tfQuantia, 1, 6);
        pane.add(lbMeiaIntera, 0, 7);
        pane.add(cbMeia, 1, 7);
        pane.add(l1, 0, 8, 2, 1);

        lbTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 23));
        lbSessao.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbData.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbIngresso.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbQuantia.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbCliente.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbMeiaIntera.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbValorIngresso.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        btnVender.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        btnCancelar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));

        this.tfDataHora.setText(cbSessao.getSelectionModel().getSelectedItem().getData().toString().replace("T", " Hora: "));
        this.tfValorIngresso.setText(String.valueOf(cbSessao.getSelectionModel().getSelectedItem().getValorIngresso()));
        this.tfIngresso.setText(String.valueOf(cbSessao.getSelectionModel().getSelectedItem().getIngressos()));
        this.tfDataHora.setDisable(true);
        this.tfValorIngresso.setDisable(true);
        this.tfIngresso.setDisable(true);
        
        return pane;
    }
}
