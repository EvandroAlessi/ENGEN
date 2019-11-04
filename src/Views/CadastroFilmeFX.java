/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.FilmeController;
import CrossCutting.Mensagem;
import Models.Filme;
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
 * Diretamente ligada ao Stage provido pela CategoriaSubFX
 * Utiliza o controlador de Filme e Filme
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Filme
 * @see Filme
 * @see FilmeController
 * @see FilmeController
 * @see CategoriaSubFX
 */
public class CadastroFilmeFX {

    private final Label lbDuracao, lbTitulo, lbDiretor, lbIdioma, lbGenero, lbTitle;
    private final TextField tfDuracao, tfTitulo, tfDiretor, tfIdioma, tfGenero;
    private final Button btnCadastrar, btnCancelar;
    private FilmeController filmeController;
    private List<Filme> filmes;
    private Filme filmeCriada;

    private Stage dialog;

    public CadastroFilmeFX() {
        filmeCriada = null;
        lbTitulo = new Label("Titulo:");
        lbDiretor = new Label("Diretor:"); 
        lbIdioma = new Label("Idioma:");
        lbGenero = new Label("Genero:");
        tfDuracao = new TextField();
        tfTitulo = new TextField();
        tfDiretor= new TextField();
        tfIdioma= new TextField(); 
        tfGenero= new TextField();
        lbTitle = new Label("Nova Filme");
        lbDuracao = new Label("Duracao:");
        btnCadastrar = new Button("Cadastrar");
        btnCancelar = new Button("Cancelar");
        filmeController = new FilmeController();
        filmes = filmeController.getAll();

        btnCadastrar.setOnAction(e -> {
            boolean ok = false;
            if (filmeCriada == null) {

                Filme nFilme = new Filme();

                try {
                    nFilme.setDuracao(Integer.parseInt(tfDuracao.getText()));
                    nFilme.setTitulo(tfTitulo.getText());
                    nFilme.setDiretor(tfDiretor.getText());
                    nFilme.setIdioma(tfIdioma.getText());
                    nFilme.setGenero(tfGenero.getText());
                    
                    filmeCriada = filmeController.create(nFilme);

                    if (filmeCriada != null) {
                        ok = true;
                    }
                } catch (Exception ex) {
                    Mensagem.informacao("O valor deve ser um número!");
                }
            } else {
                try {
                    filmeCriada.setDuracao(Integer.parseInt(tfDuracao.getText()));
                    filmeCriada.setTitulo(tfTitulo.getText());
                    filmeCriada.setDiretor(tfDiretor.getText());
                    filmeCriada.setIdioma(tfIdioma.getText());
                    filmeCriada.setGenero(tfGenero.getText());

                    filmeCriada = filmeController.update(filmeCriada);
                    if (filmeCriada != null) {
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
     * @param filme
     * @throws Exception
     */
    public void start(Stage mainStage, Filme filme) throws Exception {
        dialog = new Stage();
        GridPane painel = criarFormulario(filme);
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

    private GridPane criarFormulario(Filme filmeCriada) {
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

        tfDuracao.setPrefWidth(200);
        tfTitulo.setPrefWidth(200);
        tfTitulo.setPrefWidth(200);
        tfDiretor.setPrefWidth(200);
        tfIdioma.setPrefWidth(200);
        tfGenero.setPrefWidth(200);
        
        pane.add(lbTitle, 0, 0);
        
        pane.add(lbDuracao, 0, 1);
        
        pane.add(lbTitulo, 0, 2);
        pane.add(tfDuracao, 1, 1);
        pane.add(tfTitulo, 1, 2);
        
        pane.add(tfDiretor, 1, 3);
        pane.add(lbDiretor, 0, 3);
        
        pane.add(tfIdioma, 1, 4);
        pane.add(lbIdioma, 0, 4);
        
        pane.add(tfGenero, 1, 5);
        pane.add(lbGenero, 0, 5);
        
        pane.add(hbButtons, 0, 7, 4, 1);

        lbTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 23));
        lbTitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbDuracao.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        btnCadastrar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        btnCancelar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));

        if (filmeCriada != null) {
            this.filmeCriada = filmeCriada;
            this.lbTitle.setText("Editar Filme");
            btnCadastrar.setText("Salvar");
            
            this.tfDuracao.setText(String.valueOf(filmeCriada.getDuracao()));
            this.tfTitulo.setText(filmeCriada.getTitulo());
            this.tfDiretor.setText(filmeCriada.getDiretor());
            this.tfIdioma.setText(filmeCriada.getIdioma());
            this.tfGenero.setText(filmeCriada.getGenero());
        } 
        
        return pane;
    }

    public Filme getFilmeCriada() {
        return filmeCriada;
    }
}