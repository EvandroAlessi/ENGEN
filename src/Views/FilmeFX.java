/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.FilmeController;
import CrossCutting.Log;
import CrossCutting.Mensagem;
import Models.Filme;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 * Apresentação com Table View.
 * Diretamente ligada ao Stage provido pela MainFX
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see MainFX
 */
public class FilmeFX extends GridPane {

    Label lbTitulo;
    Button btnCadastrar, btnEditar;
    private TableView<Filme> table;
    private TableColumn tcTitulo, tcDiretor, tcDuracao, tcGenero, tcIdioma;
    private TableColumn<Filme, Void> tcApagar;
    private Stage mainStage;

    /** @param stage Recebe o stage principal de mainFX
    */
    public FilmeFX(Stage stage) {
        mainStage = stage;
        FilmeController control = new FilmeController();
        lbTitulo = new Label("Filmes");
        table = new TableView();
        tcDiretor = new TableColumn("Diretor");
        tcTitulo = new TableColumn("Titulo");
        tcDuracao = new TableColumn("Duracao");
        tcGenero = new TableColumn("Genero");
        tcIdioma = new TableColumn("Idioma");
        tcApagar = new TableColumn("Ações");
        btnCadastrar = new Button("Cadastrar");
        btnEditar = new Button("Editar");

//        tcDescricao.prefWidthProperty().bind(table.widthProperty()
//                .multiply(0.638));
//        tcTipoCategoria.prefWidthProperty().bind(table.widthProperty()
//                .multiply(0.15));
//        tcTipo.prefWidthProperty().bind(table.widthProperty()
//                .multiply(0.15));
//        tcApagar.prefWidthProperty().bind(table.widthProperty()
//                .multiply(0.06));
        tcDiretor.setCellValueFactory(new PropertyValueFactory<>("Diretor"));
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        tcDuracao.setCellValueFactory(new PropertyValueFactory<>("Duracao"));
        tcGenero.setCellValueFactory(new PropertyValueFactory<>("Genero"));
        tcIdioma.setCellValueFactory(new PropertyValueFactory<>("Idioma"));
        
         Callback<TableColumn<Filme, Void>, TableCell<Filme, Void>> cellFactory = new Callback<TableColumn<Filme, Void>, TableCell<Filme, Void>>() {
            @Override
            public TableCell<Filme, Void> call(final TableColumn<Filme, Void> param) {
                final TableCell<Filme, Void> cell = new TableCell<Filme, Void>() {

                    private final Button btn = new Button("Remover");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Alert dialog= new Alert(Alert.AlertType.WARNING);
                            ButtonType btnSim = new ButtonType("Sim", ButtonBar.ButtonData.OK_DONE);
                            ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
                            dialog.setTitle("Confimação de exclusão");
                            dialog.setHeaderText("Deseja realmente excluir?");
                            dialog.setContentText("Tem certeza?");
                            dialog.getButtonTypes().setAll(btnSim, btnNao);
                            Window window = dialog.getDialogPane().getScene().getWindow();
                            window.setOnCloseRequest(e -> dialog.hide());
                            dialog.showAndWait().ifPresent(b -> {
                                if (b == btnSim) {
                                    Filme data = getTableView().getItems().get(getIndex());
                                    control.delete(data.getFilmeID());
                                    table.getItems().remove(data);
                                    table.refresh();
                                } else {
                                    dialog.close();
                                }
                            });
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };

                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        };

        table.setRowFactory(new Callback<TableView<Filme>, TableRow<Filme>>() {
            public TableRow<Filme> call(TableView<Filme> tableView) {
                final TableRow<Filme> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem removeItem = new MenuItem("Remover");
                MenuItem editaItem = new MenuItem("Editar");
                removeItem.setOnAction(e -> {
                    Alert dialog = new Alert(Alert.AlertType.WARNING);
                    ButtonType btnSim = new ButtonType("Sim");
                    ButtonType btnNao = new ButtonType("Não");
                    dialog.setTitle("Confimação de exclusão");
                    dialog.setHeaderText("Deseja realmente excluir?");
                    dialog.setContentText("Tem certeza?");
                    dialog.getButtonTypes().setAll(btnSim, btnNao);
                    dialog.showAndWait().ifPresent(b -> {
                        if (b == btnSim) {
                            Filme data = table.getSelectionModel().getSelectedItem();
                            control.delete(data.getFilmeID());
                            table.getItems().remove(data);
                            table.refresh();
                        } else {
                            dialog.close();
                        }
                    });

                });

                editaItem.setOnAction(e -> {
                    CadastroFilmeFX form = new CadastroFilmeFX();
                    try {
                        form.start(mainStage, table.getSelectionModel().getSelectedItem());
                        table.refresh();
                    } catch (Exception ex) {
                        Log.saveLog(ex);
                        Mensagem.excecao(ex);
                    }
                });
                rowMenu.getItems().addAll(editaItem, removeItem);
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null));

                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        if (e.getClickCount() == 2 && (!row.isEmpty())) {
                            CadastroFilmeFX form = new CadastroFilmeFX();
                            try {
                                form.start(mainStage, table.getSelectionModel().getSelectedItem());
                                table.refresh();
                            } catch (Exception ex) {
                                Log.saveLog(ex);
                                Mensagem.excecao(ex);
                            }
                        }
                    }
                });

                return row;
            }
        });

        tcApagar.setCellFactory(cellFactory);
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5));
        hBox.setSpacing(10);
        btnCadastrar.setMinSize(80, 40);
        btnEditar.setMinSize(80, 40);
        hBox.getChildren().addAll(btnCadastrar, btnEditar);

        table.getColumns().addAll(tcTitulo, tcDiretor, tcDuracao, tcGenero, tcIdioma, tcApagar);
        table.setTableMenuButtonVisible(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        add(lbTitulo, 0, 0);
        add(hBox, 1, 0);
        add(table, 0, 1);

        List<Filme> categorias = control.getAll();

        this.table.setItems(FXCollections.observableArrayList(categorias));
        this.setPadding(new Insets(5));
        this.setHgap(5);
        this.setVgap(5);

        lbTitulo.setFont(new Font("Arial", 24));
        lbTitulo.setPadding(new Insets(15, 15, 15, 5));
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);

        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        r2.setVgrow(Priority.ALWAYS);
        getColumnConstraints().add(c1);
        getRowConstraints().addAll(r1, r2);
        setConstraints(lbTitulo, 0, 0, 1, 1, HPos.LEFT, VPos.BASELINE);
        setConstraints(btnCadastrar, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setColumnSpan(table, 2);

        btnEditar.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());

        btnEditar.setOnAction(e -> {
            CadastroFilmeFX form = new CadastroFilmeFX();
            try {
                form.start(mainStage, table.getSelectionModel().getSelectedItem());
                table.refresh();
            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });

        btnCadastrar.setOnAction(e -> {
            CadastroFilmeFX form = new CadastroFilmeFX();
            try {
                form.start(mainStage, null);

                if (form.getFilmeCriada() != null) {
                    table.getItems().add(form.getFilmeCriada());
                    table.refresh();
                }

            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });

        //tcNumero.setCellFactory(TextFieldTableCell.forTableColumn());
    }
}
