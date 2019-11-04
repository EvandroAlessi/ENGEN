/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controllers.SessaoController;
import CrossCutting.Log;
import CrossCutting.Mensagem;
import Models.Sessao;
import java.time.LocalDateTime;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 * View responsável pela apresentação das Sessaos.
 * Apresentação com TableView.
 * Diretamente ligada ao Stage provido pela MainFX.
 * Utiliza o controlador de Sessaos.
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Sessao
 * @see SessaoController
 * @see MainFX
 */
public class SessaoFX extends GridPane {

    Label lbTitulo;
    Button btnCadastrar, btnEditar;
    private TableView<Sessao> table;
    private TableColumn<Sessao, LocalDateTime> tcData;
    private TableColumn tcIngressos, tcValorIngresso, tcFilme, tcSala;
    private TableColumn<Sessao, Void> tcApagar;
    private Stage mainStage;

    /** @param stage Recebe o stage principal de mainFX
    */
    public SessaoFX(Stage stage) {
        mainStage = stage;
        SessaoController control = new SessaoController();
        lbTitulo = new Label("Sessões");
        table = new TableView();
        tcData = new TableColumn("Data");
        tcIngressos = new TableColumn("Ingressos");
        tcValorIngresso = new TableColumn("Valor Ingresso");
        tcFilme = new TableColumn("Filme");
        btnCadastrar = new Button("Cadastrar");
        btnEditar = new Button("Editar");
        tcSala = new TableColumn("Sala");
        tcApagar = new TableColumn("Ações");

        tcApagar.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.06));
        tcIngressos.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.15));
        tcValorIngresso.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.15));
        tcData.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.10));
        tcFilme.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.338));
        tcSala.prefWidthProperty().bind(table.widthProperty()
                .multiply(0.10));
        
        tcData.setCellValueFactory(new PropertyValueFactory<>("dateF"));
        tcIngressos.setCellValueFactory(new PropertyValueFactory<>("Ingressos"));
        tcValorIngresso.setCellValueFactory(new PropertyValueFactory<>("ValorIngresso"));
        
        tcFilme.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sessao, String>, ObservableValue<String>>() {
            @Override
            // obtem a descrição da categoria e converte para Observable
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sessao, String> param) {
                return new SimpleStringProperty(param.getValue().getFilme().getTitulo());
            }
        });
        tcSala.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sessao, String>, ObservableValue<String>>() {
            @Override
            // obtem a descrição da subcategoria e converte para Observable
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sessao, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getSala().getNumero()));
            }
        });

        Callback<TableColumn<Sessao, Void>, TableCell<Sessao, Void>> cellFactory = new Callback<TableColumn<Sessao, Void>, TableCell<Sessao, Void>>() {
            @Override
            public TableCell<Sessao, Void> call(final TableColumn<Sessao, Void> param) {
                final TableCell<Sessao, Void> cell = new TableCell<Sessao, Void>() {

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
                                    Sessao data = getTableView().getItems().get(getIndex());
                                    control.delete(data.getSessaoID());
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
        
        table.setRowFactory(new Callback<TableView<Sessao>, TableRow<Sessao>>() {
            public TableRow<Sessao> call(TableView<Sessao> tableView) {
                final TableRow<Sessao> row = new TableRow<>();
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
                            Sessao data = table.getSelectionModel().getSelectedItem();
                            control.delete(data.getSessaoID());
                            table.getItems().remove(data);
                            table.refresh();
                            table.getItems().remove(row.getItem());
                        } else {
                            dialog.close();
                        }
                    });
                    
                });

                editaItem.setOnAction(e -> {
                    CadastroSessaoFX form = new CadastroSessaoFX();
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
                            CadastroSessaoFX form = new CadastroSessaoFX();
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
        table.getColumns().addAll(tcData, tcIngressos, tcValorIngresso, tcFilme, tcSala, tcApagar);
        table.setTableMenuButtonVisible(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tcFilme.setVisible(false);
        //tcSala.setVisible(false);
        
        HBox hBox = new HBox();
        hBox.getChildren().addAll(btnCadastrar, btnEditar);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(5));

        add(lbTitulo, 0, 0);
        add(hBox, 1, 0);
        add(table, 0, 1);

        List<Sessao> despesas = control.getAll();

        this.table.setItems(FXCollections.observableArrayList(despesas));
        this.setPadding(new Insets(5));
        this.setHgap(5);
        this.setVgap(5);
        
        lbTitulo.setFont(new Font("Arial", 24));
        lbTitulo.setPadding(new Insets(15, 15, 15, 5));
        btnCadastrar.setMinSize(80, 40);
        btnEditar.setMinSize(80, 40);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);

        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        //r1.setPercentHeight(5);
        r2.setVgrow(Priority.ALWAYS);
        getColumnConstraints().add(c1);
        getRowConstraints().addAll(r1, r2);
        setConstraints(lbTitulo, 0, 0, 1, 1, HPos.LEFT, VPos.BASELINE);
        setConstraints(btnCadastrar, 0, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
        GridPane.setColumnSpan(table, 2);

        btnEditar.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());

        btnEditar.setOnAction(e -> {
            CadastroSessaoFX form = new CadastroSessaoFX();
            try {
                form.start(mainStage, table.getSelectionModel().getSelectedItem());
                table.refresh();
            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });

        btnCadastrar.setOnAction(e -> {
            CadastroSessaoFX form = new CadastroSessaoFX();
            try {
                form.start(mainStage, null);
                if (form.getSessaoCriada() != null) {
                    table.getItems().add(form.getSessaoCriada());
                    table.refresh();
                }

            } catch (Exception ex) {
                Log.saveLog(ex);
                Mensagem.excecao(ex);
            }
        });
    }
}
