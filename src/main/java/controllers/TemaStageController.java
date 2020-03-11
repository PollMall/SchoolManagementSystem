package controllers;

import domains.Tema;
import services.NotaService;
import services.StudentService;
import utils.ChangeOperation;
import utils.events.Event;
import utils.events.TemaChangeEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.observers.Observer;
import services.TemaService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TemaStageController implements Observer<TemaChangeEvent> {
    private ChangeOperation changeOperation;
    private Tema oldData;
    private Tema changedData;
    private TemaService temaService;
    private ArrayList<Tema> backupList=new ArrayList<>();
    private ObservableList<Tema> writeList = FXCollections.observableArrayList();
    private Stage noteStage;
    private Stage currentStage;
    @FXML
    private Button undoBtn;
    @FXML
    private TableView<Tema> temeTableView;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn descriereColumn;
    @FXML
    private TableColumn startWeekColumn;
    @FXML
    private TableColumn deadlineWeekColumn;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private TextField searchDescriere;
    @FXML
    private TextField searchStartWeek;
    @FXML
    private TextField searchDeadlineWeek;

    public void setService(TemaService temaService,Stage currentStage,Stage noteStage) {
        this.temaService=temaService;
        this.currentStage=currentStage;
        this.noteStage=noteStage;
        this.temaService.addObserver(this);
        initList();
    }

    private void initList() {
        this.backupList = (ArrayList<Tema>) temaService.serviceFindAll();
        this.writeList.addAll((ArrayList<Tema>) temaService.serviceFindAll());
    }

    @FXML
    public void initialize() {

        //Binding data to tableView
        temeTableView.setItems(writeList);
        //set cell value factory method
        idColumn.setCellValueFactory(new PropertyValueFactory<Tema, String>("id"));
        descriereColumn.setCellValueFactory(new PropertyValueFactory<Tema, String>("descriere"));
        startWeekColumn.setCellValueFactory(new PropertyValueFactory<Tema, String>("startWeek"));
        deadlineWeekColumn.setCellValueFactory(new PropertyValueFactory<Tema, String>("deadlineWeek"));

        //SEARCH DESCRIERE
        searchDescriere.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                writeList.setAll(backupList
                        .stream()
                        .filter(tema -> {
                            return (tema.getDescriere().startsWith(newValue) && tema.getStartWeek().toString().startsWith(searchStartWeek.getText())
                                    && tema.getDeadlineWeek().toString().startsWith(searchDeadlineWeek.getText()));
                        }).collect(Collectors.toList())
                );
            }
        });

        //SEARCH START WEEK
        searchStartWeek.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                writeList.setAll(backupList
                        .stream()
                        .filter(tema -> {
                            return (tema.getDescriere().startsWith(searchDescriere.getText()) && tema.getStartWeek().toString().startsWith(newValue)
                                    && tema.getDeadlineWeek().toString().startsWith(searchDeadlineWeek.getText()));
                        }).collect(Collectors.toList())
                );
            }
        });

        //SEARCH DEADLINE WEEK
        searchDeadlineWeek.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                writeList.setAll(backupList
                        .stream()
                        .filter(tema -> {
                            return (tema.getDescriere().startsWith(searchDescriere.getText()) && tema.getStartWeek().toString().startsWith(searchStartWeek.getText())
                                    && tema.getDeadlineWeek().toString().startsWith(newValue));
                        }).collect(Collectors.toList())
                );
            }
        });
    }

    @FXML
    public void handleAdd(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/ModifyStage.fxml"));
        AnchorPane root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("ADD");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        ModifyController controller = loader.getController();
        controller.setService(temaService,dialogStage,null);

        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        backupList= (ArrayList<Tema>) temaService.serviceFindAll();
        dialogStage.showAndWait();
    }

    @FXML
    public void handleUpdate(ActionEvent actionEvent) throws IOException {
        if(temeTableView.getSelectionModel().isEmpty()) {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("O_o");
            alert.setContentText("Trebuie sa selectati o tema");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ModifyStage.fxml"));
            AnchorPane root=loader.load();

            Stage dialogStage=new Stage();
            dialogStage.setTitle("UPDATE");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            ModifyController controller=loader.getController();
            controller.setService(temaService, dialogStage, temeTableView.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        }
    }

    @FXML
    public void handleRemove(ActionEvent actionEvent) {
        Alert alert=new Alert(Alert.AlertType.WARNING);
        if(temeTableView.getSelectionModel().isEmpty()){
            alert.setHeaderText("O_o");
            alert.setContentText("Trebuie sa selectati o tema");
        }
        else {
            Tema tema = temeTableView.getSelectionModel().getSelectedItem();
            temaService.serviceDelete(tema.getId());
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("^_^");
            alert.setContentText("Tema stearsa cu succes");
        }
        alert.showAndWait();
    }

    @Override
    public void update(TemaChangeEvent temaChangeEvent) {
        writeList.setAll((List<Tema>) temaService.serviceFindAll());
        changeOperation=temaChangeEvent.getChangeOperation();
        switch (changeOperation){
            case ADD:
                changedData=temaChangeEvent.getChangedData();
                break;
            case UPDATE:
                oldData=temaChangeEvent.getOldData();
                changedData=temaChangeEvent.getChangedData();
                break;
            case DELETE:
                changedData=temaChangeEvent.getChangedData();
                break;
        }
    }

    public void handleToNote(ActionEvent actionEvent) throws IOException {
        currentStage.close();
        noteStage.show();
        resetFields();
    }

    public void handleUndo(ActionEvent actionEvent) {
        switch (changeOperation){
            case ADD:
                temaService.serviceDelete(changedData.getId());
                break;
            case UPDATE:
                temaService.serviceUpdate(oldData);
                break;
            case DELETE:
                temaService.serviceSave(changedData);
                break;
        }
    }

    public void resetFields(){
        searchDescriere.setText("");
        searchStartWeek.setText("");
        searchDeadlineWeek.setText("");
    }
}
