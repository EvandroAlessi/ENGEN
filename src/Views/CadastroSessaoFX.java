/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.FilmeController;
import Controllers.SalaController;
import Controllers.SessaoController;
import CrossCutting.DateTimePicker;
import CrossCutting.Mensagem;
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
public class CadastroSessaoFX {

    private final Label lbData, lbSala, lbFilme, lbValorIngresso, lbIngresso, lbTitle;
    private final TextField tfValorIngresso, tfIngresso;
    private final DateTimePicker dpCalendario;
    private final Button btnCadastrar, btnCancelar;
    private Stage dialog;
    private Sessao sessaoCriada;
    private SessaoController sessaoController;
    private final List<Sala> salas;
    private final List<Filme> filmes;
    private ComboBox<Sala> cbSala;
    private ComboBox<Filme> cbFilme;
    private FilmeController filmeController;
    private SalaController salaController;
    
    public CadastroSessaoFX() {
        sessaoCriada = null;
        lbFilme = new Label("Filme:");
        tfValorIngresso = new TextField();
        tfIngresso = new TextField();
        lbTitle = new Label("Nova Sessão");
        lbData = new Label("Data:");
        lbSala = new Label("Sala:");
        lbValorIngresso = new Label("Valor:");
        lbIngresso = new Label("Ingressos:");
        btnCadastrar = new Button("Cadastrar");
        btnCancelar = new Button("Cancelar");
        dpCalendario = new DateTimePicker();
        filmeController = new FilmeController();
        salaController = new SalaController();
        sessaoController = new SessaoController();
        salas = salaController.getAll();
        filmes = filmeController.getAll();
        cbFilme = new ComboBox();
        cbSala = new ComboBox();

        btnCadastrar.setOnAction(e -> {
            boolean ok = true;
            //if (tfIngressos.getText().trim().length() > 0 && tfIngressos.getText() != null) {
                //if (Double.parseDouble(tfValorIngresso.getText() > 0 && tfValorIngresso.getText() != null) {
                    //if (cbSubCategoria.getSelectionModel().getSelectedItem() != null) {
                        //ok = true;
                    //} else {
                        //Mensagem.aviso("A Despesa deve ter um Categoria.");
                    //}
                //} else {
                   // tfValorIngresso.setStyle("-fx-text-box-border: red ;"
                            //+ " -fx-focus-color: red ;");
                    //Mensagem.informacao("A Despesa deve ter um valor!");
                //}
            //} else {
//                tfIngressos.setStyle("-fx-text-box-border: red ;"
//                        + " -fx-focus-color: red ;");
//                Mensagem.informacao("A Despesa deve ter uma Descrição!");
//                //if (cbSubCategoria.getSelectionModel().getSelectedItem() == null) {
//                    //Mensagem.aviso("A Despesa deve ter um Categoria.");
//                //}
//                if (tfValorIngresso.getText().trim().length() == 0 || tfValorIngresso.getText() == null) {
//                    tfValorIngresso.setStyle("-fx-text-box-border: red ;"
//                            + " -fx-focus-color: red ;");
//                    Mensagem.informacao("A Despesa deve ter um valor!");
//                }
//            }
            if (ok) {
                ok = false;
                if (sessaoCriada == null) {

                    Sessao nSessao = new Sessao();

                    try {
                        nSessao.setData(dpCalendario.getDateTimeValue());
                        nSessao.setSala(cbSala.getSelectionModel().getSelectedItem());
                        nSessao.setFilme(cbFilme.getSelectionModel().getSelectedItem());
                        nSessao.setIngressos(Integer.parseInt(tfIngresso.getText()));
                        nSessao.setValorIngresso(Double.parseDouble(tfValorIngresso.getText()));
                        
                        sessaoCriada = sessaoController.create(nSessao);
                        
                        if (sessaoCriada != null) {
                            ok = true;
                        }
                    } catch (Exception ex) {
                        Mensagem.informacao("O valor deve ser um número!");
                    }
                } else {
                    try {
                        sessaoCriada.setData(dpCalendario.getDateTimeValue());
                        sessaoCriada.setSala(cbSala.getSelectionModel().getSelectedItem());
                        sessaoCriada.setFilme(cbFilme.getSelectionModel().getSelectedItem());
                        sessaoCriada.setIngressos(Integer.parseInt(tfIngresso.getText()));
                        sessaoCriada.setValorIngresso(Double.parseDouble(tfValorIngresso.getText()));
                        
                        sessaoCriada = sessaoController.update(sessaoCriada);
                        if (sessaoCriada != null) {
                            ok = true;
                        }
                    } catch (Exception ex) {
                        Mensagem.informacao("O valor deve ser um número!");
                    }
                }

                if (ok) {
                    dialog.close();
                }
            }
        });

        btnCancelar.setOnAction(e -> {
            dialog.close();
        });
    }

    public void start(Stage mainStage, Sessao sessaoCriada) throws Exception {
        dialog = new Stage();
        GridPane painel = criarFormulario(sessaoCriada);
        dialog.initOwner(mainStage);
        dialog.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(painel, 400, 420);

        dialog.setResizable(false);
        dialog.setMaxHeight(420);
        dialog.setMaxWidth(400);
        dialog.setMinHeight(420);
        dialog.setMinWidth(400);

        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private GridPane criarFormulario(Sessao sessaoCriada) {
        GridPane pane = new GridPane();
        HBox l1 = new HBox();
//        List<Filme> filmes = new ArrayList<>();
//        List<Sala> salas = new ArrayList<>();


        ListIterator<Filme> subIte = filmes.listIterator();
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 10, 25, 25));

        cbSala.setItems(FXCollections.observableArrayList(salas));
        
                
        cbSala.getSelectionModel().selectedItemProperty().addListener((opcoes, valorAntigo, valorNovo) -> {
            tfIngresso.setText(String.valueOf(salaController.getCapacidade(cbSala.getSelectionModel().getSelectedItem().getSalaID())));
        });

        cbFilme.setItems(FXCollections.observableArrayList(filmes));


        ColumnConstraints c1 = new ColumnConstraints();
        //c1.setHgrow(Priority.ALWAYS);
        c1.setHalignment(HPos.CENTER);

        RowConstraints r1 = new RowConstraints();
        //r1.setVgrow(Priority.ALWAYS);
        r1.setValignment(VPos.CENTER);

        pane.getColumnConstraints().add(c1);
        pane.getRowConstraints().add(r1);

        cbSala.getSelectionModel().selectFirst();

        l1.getChildren().addAll(btnCadastrar, btnCancelar);
        l1.setSpacing(10);
        l1.setPadding(new Insets(35, 0, -10, 0));
        l1.setAlignment(Pos.CENTER);

        lbTitle.setPadding(new Insets(0, 0, 25, 0));
        lbTitle.setAlignment(Pos.CENTER);

        cbFilme.getSelectionModel().selectFirst();
        dpCalendario.setValue(LocalDate.now());

        cbSala.setPrefWidth(200);
        cbFilme.setPrefWidth(200);
        tfValorIngresso.setPrefWidth(200);
        dpCalendario.setPrefWidth(200);

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
        pane.add(cbSala, 1, 1);
        pane.add(lbSala, 0, 1);
        pane.add(cbFilme, 1, 2);
        pane.add(lbFilme, 0, 2);
        pane.add(lbValorIngresso, 0, 3);
        pane.add(tfValorIngresso, 1, 3);
        pane.add(lbData, 0, 4);
        pane.add(dpCalendario, 1, 4);
        pane.add(lbIngresso, 0, 5);
        pane.add(tfIngresso, 1, 5);
        pane.add(l1, 0, 7, 2, 1);

        lbTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 23));
        lbFilme.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbData.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbIngresso.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbSala.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        lbValorIngresso.setFont(Font.font("Arial", FontWeight.NORMAL, 17));
        btnCadastrar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        btnCancelar.setFont(Font.font("Arial", FontWeight.NORMAL, 15));

        if (sessaoCriada != null) {
            this.sessaoCriada = sessaoCriada;
            this.lbTitle.setText("Editar Despesa");
            btnCadastrar.setText("Salvar");
            this.dpCalendario.setValue(sessaoCriada.getData().toLocalDate());
            this.tfValorIngresso.setText(String.valueOf(sessaoCriada.getValorIngresso()));

            for (Filme filme : this.cbFilme.getItems()) {
                if (filme.getFilmeID() == sessaoCriada.getFilmeID()) {
                    this.cbFilme.getSelectionModel().select(filme);
                }
            }

            for (Sala sala : this.cbSala.getItems()) {
                if (sala.getSalaID() == sessaoCriada.getSalaID()) {
                    this.cbSala.getSelectionModel().select(sala);
                }
            }

            //this.calendario.setValue(LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(despesaCriada.getDataOcorrencia()) ));
        }

        return pane;
    }

    public Sessao getSessaoCriada() {
        return sessaoCriada;
    }
}
