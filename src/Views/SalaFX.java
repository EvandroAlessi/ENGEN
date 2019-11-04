/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.SalaController;
import CrossCutting.Log;
import CrossCutting.Mensagem;
import Models.Sala;
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
public class SalaFX extends GridPane {

    Label lbTitulo;
    Button btnCadastrar, btnEditar;
    private TableView<Sala> table;
    private TableColumn tcCapacidade, tcNumero;
    private TableColumn<Sala, Void> tcApagar;
    private Stage mainStage;

    /** @param stage Recebe o stage principal de mainFX
    */
    public SalaFX(Stage stage) {
        mainStage = stage;
        SalaController control = new SalaController();
        lbTitulo = new Label("Salas");
        table = new TableView();
        tcNumero = new TableColumn("Numero");
        tcCapacidade = new TableColumn("Capacidade");
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
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("Numero"));
        tcCapacidade.setCellValueFactory(new PropertyValueFactory<>("Capacidade"));
        
         Callback<TableColumn<Sala, Void>, TableCell<Sala, Void>> cellFactory = new Callback<TableColumn<Sala, Void>, TableCell<Sala, Void>>() {
            @Override
            public TableCell<Sala, Void> call(final TableColumn<Sala, Void> param) {
                final TableCell<Sala, Void> cell = new TableCell<Sala, Void>() {

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
                                    Sala data = getTableView().getItems().get(getIndex());
                                    control.delete(data.getSalaID());
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

        table.setRowFactory(new Callback<TableView<Sala>, TableRow<Sala>>() {
            public TableRow<Sala> call(TableView<Sala> tableView) {
                final TableRow<Sala> row = new TableRow<>();
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
                            Sala data = table.getSelectionModel().getSelectedItem();
                            control.delete(data.getSalaID());
                            table.getItems().remove(data);
                            table.refresh();
                        } else {
                            dialog.close();
                        }
                    });

                });

                editaItem.setOnAction(e -> {
                    CadastroSalaFX form = new CadastroSalaFX();
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
                            CadastroSalaFX form = new CadastroSalaFX();
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

        table.getColumns().addAll(tcCapacidade, tcNumero, tcApagar);
        table.setTableMenuButtonVisible(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        add(lbTitulo, 0, 0);
        add(hBox, 1, 0);
        add(table, 0, 1);

        List<Sala> categorias = control.getAll();

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
            CadastroSalaFX form = new CadastroSalaFX();
            try {
                form.start(mainStage, table.getSelectionModel().getSelectedItem());
                table.refresh();
            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });

        btnCadastrar.setOnAction(e -> {
            CadastroSalaFX form = new CadastroSalaFX();
            try {
                form.start(mainStage, null);

                if (form.getSalaCriada() != null) {
                    table.getItems().add(form.getSalaCriada());
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
