/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.SalaController;
import CrossCutting.Mensagem;
import Models.Sala;
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
 * View com formulário de cadastro para Categorias.
 * Diretamente ligada ao Stage provido pela CategoriaSubFX
 * Utiliza o controlador de Sala e Sala
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Sala
 * @see Sala
 * @see SalaController
 * @see SalaController
 * @see CategoriaSubFX
 */
public class CadastroSalaFX {

    private final Label lbNumero, lbCapacidade, lbTitle;
    private final TextField tfNumero, tfCapacidade;
    private final Button btnCadastrar, btnCancelar;
    private SalaController salaController;
    private List<Sala> salas;
    private Sala salaCriada;

    private Stage dialog;

    public CadastroSalaFX() {
        salaCriada = null;
        lbCapacidade = new Label("Capacidade:");
        tfNumero = new TextField();
        tfCapacidade = new TextField();
        lbTitle = new Label("Nova Sala");
        lbNumero = new Label("Numero:");
        btnCadastrar = new Button("Cadastrar");
        btnCancelar = new Button("Cancelar");
        salaController = new SalaController();
        salas = salaController.getAll();

        btnCadastrar.setOnAction(e -> {
            boolean ok = false;
            if (salaCriada == null) {

                Sala nSala = new Sala();

                try {
                    nSala.setNumero(Integer.parseInt(tfNumero.getText()));
                    nSala.setCapacidade(Integer.parseInt(tfCapacidade.getText()));

                    salaCriada = salaController.create(nSala);

                    if (salaCriada != null) {
                        ok = true;
                    }
                } catch (Exception ex) {
                    Mensagem.informacao("O valor deve ser um número!");
                }
            } else {
                try {
                    salaCriada.setNumero(Integer.parseInt(tfNumero.getText()));
                    salaCriada.setCapacidade(Integer.parseInt(tfCapacidade.getText()));

                    salaCriada = salaController.update(salaCriada);
                    if (salaCriada != null) {
                        ok = true;
                    }
                } catch (Exception ex) {
                    Mensagem.informacao("O valor deve ser um número!");
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
     * @param sala
     * @throws Exception
     */
    public void start(Stage mainStage, Sala sala) throws Exception {
        dialog = new Stage();
        GridPane painel = criarFormulario(sala);
        dialog.initOwner(mainStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(painel, 430, 380);
        
        dialog.setResizable(false);
        dialog.setMaxHeight(380);
        dialog.setMaxWidth(430);
        dialog.setMinHeight(380);
        dialog.setMinWidth(430);

        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private GridPane criarFormulario(Sala salaCriada) {
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

        tfNumero.setPrefWidth(200);
        tfCapacidade.setPrefWidth(200);
        
        pane.add(lbTitle, 0, 0);
        
        pane.add(lbNumero, 0, 1);
        
        pane.add(lbCapacidade, 0, 2);
        pane.add(tfNumero, 1, 1);
        pane.add(tfCapacidade, 1, 2);
        
        pane.add(hbButtons, 0, 5, 4, 1);

        lbTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 23));
        lbCapacidade.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbNumero.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        btnCadastrar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        btnCancelar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));

        if (salaCriada != null) {
            this.salaCriada = salaCriada;
            this.lbTitle.setText("Editar Categoria");
            btnCadastrar.setText("Salvar");
            this.tfNumero.setText(String.valueOf(salaCriada.getNumero()));
            this.tfCapacidade.setText(String.valueOf(salaCriada.getCapacidade()));
        } 
        
        return pane;
    }

    public Sala getSalaCriada() {
        return salaCriada;
    }
}