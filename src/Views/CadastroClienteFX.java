/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.ClienteController;
import CrossCutting.DateTimePicker;
import CrossCutting.Mensagem;
import Models.Cliente;
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
 * Utiliza o controlador de Cliente e Cliente
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Cliente
 * @see Cliente
 * @see ClienteController
 * @see ClienteController
 * @see CategoriaSubFX
 */
public class CadastroClienteFX {

    private final Label lbPontuacao, lbCPF, lbNome, lbDataNascimento, lbTitle;
    private final TextField tfPontuacao, tfCPF, tfNome;
     private final DatePicker dpCalendario;
    private final Button btnCadastrar, btnCancelar;
    private ClienteController clienteController;
    private List<Cliente> clientes;
    private Cliente clienteCriada;

    private Stage dialog;

    public CadastroClienteFX() {
        clienteCriada = null;
        lbCPF = new Label("CPF:");
        lbNome = new Label("Nome:"); 
        lbDataNascimento = new Label("DataNascimento:");
        dpCalendario = new DatePicker();
        tfPontuacao = new TextField();
        tfCPF = new TextField();
        tfNome= new TextField();
        lbTitle = new Label("Nova Cliente");
        lbPontuacao = new Label("Pontuacao:");
        btnCadastrar = new Button("Cadastrar");
        btnCancelar = new Button("Cancelar");
        clienteController = new ClienteController();
        clientes = clienteController.getAll();

        btnCadastrar.setOnAction(e -> {
            boolean ok = false;
            if (clienteCriada == null) {

                Cliente nCliente = new Cliente();

                try {
                    nCliente.setPontuacao(Integer.parseInt(tfPontuacao.getText()));
                    nCliente.setCPF(tfCPF.getText());
                    nCliente.setNome(tfNome.getText());
                    nCliente.setDataNascimento(dpCalendario.getValue());
                    
                    clienteCriada = clienteController.create(nCliente);

                    if (clienteCriada != null) {
                        ok = true;
                    }
                } catch (Exception ex) {
                    Mensagem.informacao("Alguns dos valores informados está no formato incorreto!");
                }
            } else {
                try {
                    clienteCriada.setPontuacao(Integer.parseInt(tfPontuacao.getText()));
                    clienteCriada.setCPF(tfCPF.getText());
                    clienteCriada.setNome(tfNome.getText());
                    clienteCriada.setDataNascimento(dpCalendario.getValue());

                    clienteCriada = clienteController.update(clienteCriada);
                    if (clienteCriada != null) {
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
     * @param cliente
     * @throws Exception
     */
    public void start(Stage mainStage, Cliente cliente) throws Exception {
        dialog = new Stage();
        GridPane painel = criarFormulario(cliente);
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

    private GridPane criarFormulario(Cliente clienteCriada) {
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

        dpCalendario.setValue(LocalDate.now());
        
        tfPontuacao.setPrefWidth(200);
        tfCPF.setPrefWidth(200);
        tfCPF.setPrefWidth(200);
        tfNome.setPrefWidth(200);
        dpCalendario.setPrefWidth(200);
        
        pane.add(lbTitle, 0, 0);
        
        pane.add(lbPontuacao, 0, 1);
        
        pane.add(lbCPF, 0, 2);
        pane.add(tfPontuacao, 1, 1);
        pane.add(tfCPF, 1, 2);
        
        pane.add(tfNome, 1, 3);
        pane.add(lbNome, 0, 3);
        
        pane.add(dpCalendario, 1, 4);
        pane.add(lbDataNascimento, 0, 4);
        
        pane.add(hbButtons, 0, 7, 4, 1);

        lbTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 23));
        lbCPF.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbPontuacao.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        btnCadastrar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        btnCancelar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));

        if (clienteCriada != null) {
            this.clienteCriada = clienteCriada;
            this.lbTitle.setText("Editar Cliente");
            btnCadastrar.setText("Salvar");
            
            this.tfPontuacao.setText(String.valueOf(clienteCriada.getPontuacao()));
            this.tfCPF.setText(clienteCriada.getCPF());
            this.tfNome.setText(clienteCriada.getNome());
            this.dpCalendario.setValue(clienteCriada.getDataNascimento());
        } 
        
        return pane;
    }

    public Cliente getClienteCriada() {
        return clienteCriada;
    }
}