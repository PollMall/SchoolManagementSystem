package controllers;

import domains.Student;
import domains.Tema;
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
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import services.NotaService;
import services.StudentService;
import services.TemaService;
import utils.events.TemaChangeEvent;
import utils.observers.Observer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotaStageController implements Observer<TemaChangeEvent> {

    private static final String PENALIZARE1="----NOTA A FOST DIMINUATA CU 1 PUNCT DIN CAUZA INTARZIERILOR----";
    private static final String PENALIZARE2="----NOTA A FOST DIMINUATA CU 2 PUNCTE DIN CAUZA INTARZIERILOR----";
    private static final DateTimeFormatter DATE_TIME_FORMATTER=DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private List<LocalDate> dosarMotivari=new ArrayList<>();
    private NotaService notaService;
    private StudentService studentService;
    private TemaService temaService;
    private ObservableList<Student> writeList=FXCollections.observableArrayList();
    private List<Student> backupList=new ArrayList<>();
    private ObservableList<Tema> modelTema=FXCollections.observableArrayList();
    private Tema temaCurenta;
    private Stage temaStage;
    private Stage currentStage;
    private Integer saptamanaCurenta;
    @FXML
    private Label predareTemaLabel;
    @FXML
    private Label formatData1;
    @FXML
    private Label formatData2;
    @FXML
    private Label profesorNotificationLabel;
    @FXML
    private Label notaNotificationLabel;
    @FXML
    private TableColumn idTableColumn;
    @FXML
    private TableColumn numeTableColumn;
    @FXML
    private TableColumn prenumeTableColumn;
    @FXML
    private TableColumn grupaTableColumn;
    @FXML
    private ComboBox<Tema> temeComboBox;
    @FXML
    private TableView<Student> studentiTableView;
    @FXML
    private TextField profesorTextField;
    @FXML
    private TextField notaTextField;
    @FXML
    private TextArea descriereTextArea;
    @FXML
    private TextField numeTextField;
    @FXML
    private CheckBox motivatCheckBox;
    @FXML
    private CheckBox intarziereCheckBox;
    @FXML
    private TextField dataMotivareTextField;
    @FXML
    private TextField prenumeTextField;
    @FXML
    private Button addMotivareBtn;
    @FXML
    private TextField dataPredareTextField;

    public void setService(StudentService studentService,TemaService temaService,NotaService notaService,Stage temaStage,Stage currentStage){
        this.studentService=studentService;
        this.temaService=temaService;
        this.notaService=notaService;
        this.temaStage=temaStage;
        this.currentStage=currentStage;
        saptamanaCurenta=temaService.determinare_saptamana(LocalDate.now());
        for(Tema t:temaService.serviceFindAll()){
            if(t.getDeadlineWeek().equals(saptamanaCurenta)){
                this.temaCurenta=t;
                break;
            }
        }
        temaService.addObserver(this);
        initList();
    }
    private void initList(){
        this.backupList = (ArrayList<Student>) this.studentService.serviceFindAll();
        this.writeList.addAll((ArrayList<Student>) this.studentService.serviceFindAll());
        this.modelTema.addAll((ArrayList<Tema>)this.temaService.serviceFindAll());
        temeComboBox.getSelectionModel().select(temaCurenta);
    }
    private void initFields(){
        //motivare
        motivatCheckBox.setSelected(false);
        motivatCheckBox.setVisible(false);
        formatData1.setVisible(false);
        dataMotivareTextField.setText("");
        dataMotivareTextField.setVisible(false);
        addMotivareBtn.setVisible(false);
        //intarziere profesor
        intarziereCheckBox.setSelected(false);
        intarziereCheckBox.setVisible(false);
        formatData2.setVisible(false);
        dataPredareTextField.setText("");
        dataPredareTextField.setVisible(false);
        predareTemaLabel.setVisible(false);
        predareTemaLabel.setText("data vida");
        predareTemaLabel.setTextFill(Paint.valueOf("black"));
    }

    private void resetFields(){
        initFields();
        saptamanaCurenta=temaService.determinare_saptamana(LocalDate.now());
        for(Tema t:temaService.serviceFindAll()){
            if(t.getDeadlineWeek().equals(saptamanaCurenta)){
                this.temaCurenta=t;
                break;
            }
        }
        temeComboBox.getSelectionModel().select(temaCurenta);
        numeTextField.setText("");
        prenumeTextField.setText("");
        profesorTextField.setText("");
        notaTextField.setText("");
        descriereTextArea.setText("");
    }
    public void initialize(){
        initFields();
        //binding data to table
        studentiTableView.setItems(writeList);
        //set cell value factory method
        idTableColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("id"));
        numeTableColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("nume"));
        prenumeTableColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("prenume"));
        grupaTableColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("grupa"));

        //SEARCH NUME
        numeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                writeList.setAll(backupList
                        .stream()
                        .filter(x->{
                            return x.getNume().startsWith(numeTextField.getText()) && x.getPrenume().startsWith(prenumeTextField.getText());
                        }).collect(Collectors.toList()));
            }
        });
        //SEARCH PRENUME
        prenumeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                writeList.setAll(backupList
                        .stream()
                        .filter(x->{
                            return x.getNume().startsWith(numeTextField.getText()) && x.getPrenume().startsWith(prenumeTextField.getText());
                        }).collect(Collectors.toList())
                );
            }
        });
        //binding data to ComboBox
        temeComboBox.setItems(modelTema);
        //set
        temeComboBox.setCellFactory(new Callback<ListView<Tema>, ListCell<Tema>>() {
            @Override
            public ListCell<Tema> call(ListView<Tema> temaListView) {
                return new ListCell<Tema>(){
                    @Override
                    protected void updateItem(Tema item,boolean empty){
                        super.updateItem(item,empty);
                        if(item==null || empty){
                            setText(null);
                        }
                        else{
                            setText(item.getId().toString());
                        }
                    }
                };
            }
        });
        temeComboBox.setConverter(new StringConverter<Tema>() {
            @Override
            public String toString(Tema tema) {
                if(tema==null){
                    return null;
                }
                else{
                    return tema.getId().toString();
                }
            }

            @Override
            public Tema fromString(String s) {
                return null;
            }
        });

        //nota ChangeListener
        notaNotificationLabel.setText("nota vida");
        notaTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                Integer valoare;
                if (!newValue.equals("")) {
                    try {
                        valoare = Integer.parseInt(newValue);
                        if (valoare >= 1 && valoare <= 10) {
                            notaNotificationLabel.setText("ok");
                            notaNotificationLabel.setTextFill(Paint.valueOf("green"));
                        } else {
                            notaNotificationLabel.setText("nota nu este intre 1 si 10");
                            notaNotificationLabel.setTextFill(Paint.valueOf("red"));
                        }
                    } catch (NumberFormatException nfe) {
                        notaNotificationLabel.setText("trebuie sa contina doar cifre");
                        notaNotificationLabel.setTextFill(Paint.valueOf("red"));
                    }
                }
                else{
                    notaNotificationLabel.setText("nota vida");
                    notaNotificationLabel.setTextFill(Paint.valueOf("black"));
                }
            }
        });
        //profesor ChangeListener
        profesorNotificationLabel.setText("nume profesor vid");
        profesorTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(newValue.equals("")){
                    profesorNotificationLabel.setText("nume profesor vid");
                    profesorNotificationLabel.setTextFill(Paint.valueOf("black"));
                }
                else{
                    if(newValue.contains("0") || newValue.contains("1") || newValue.contains("2") || newValue.contains("3") || newValue.contains("4") ||
                            newValue.contains("5") || newValue.contains("6") || newValue.contains("7") || newValue.contains("8") || newValue.contains("9")){
                        profesorNotificationLabel.setText("numele nu trebuie contine cifre");
                        profesorNotificationLabel.setTextFill(Paint.valueOf("red"));
                    }
                    else{
                        profesorNotificationLabel.setText("ok");
                        profesorNotificationLabel.setTextFill(Paint.valueOf("green"));
                    }
                }
            }
        });

        //studentiTableView ChangeListener
        studentiTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observableValue, Student student, Student t1) {
                dosarMotivari=new ArrayList<>();

                temeComboBox.getSelectionModel().select(temaCurenta);
                profesorTextField.setText("");
                notaTextField.setText("");
                descriereTextArea.setText("");
            }
        });

        //handle adaugare date calendaristice
        dataMotivareTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(newValue.equals("")){
                    addMotivareBtn.setVisible(false);
                }
                else{
                    LocalDate localDate;
                    try{
                        localDate=LocalDate.parse(dataMotivareTextField.getText(), DATE_TIME_FORMATTER);
                        addMotivareBtn.setVisible(true);
                    }catch (DateTimeParseException dtpe){
                        addMotivareBtn.setVisible(false);
                    }
                }
            }
        });

        dataPredareTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue){
                if(newValue.equals("")){
                    predareTemaLabel.setText("data vida");
                    predareTemaLabel.setTextFill(Paint.valueOf("black"));
                }
                else{
                    LocalDate localDate;
                    try{
                        localDate=LocalDate.parse(dataPredareTextField.getText(), DATE_TIME_FORMATTER);
                        predareTemaLabel.setText("ok");
                        predareTemaLabel.setTextFill(Paint.valueOf("green"));
                    }catch (DateTimeParseException dtpe){
                        predareTemaLabel.setText("nu respecta formatul");
                        predareTemaLabel.setTextFill(Paint.valueOf("red"));
                    }
                }
            }
        });

    }

    public void handleToTeme(ActionEvent actionEvent) throws IOException {
        currentStage.close();
        temaStage.show();
        resetFields();
    }

    public void handleComboBox(ActionEvent actionEvent) {
        if(temeComboBox.getSelectionModel().getSelectedItem()!=null &&((Tema)temeComboBox.getSelectionModel().getSelectedItem()).getDeadlineWeek()<temaService.determinare_saptamana(LocalDate.now())){
            formatData1.setVisible(false);
            dataMotivareTextField.setVisible(false);
            dataMotivareTextField.setText("");
            addMotivareBtn.setVisible(false);
            motivatCheckBox.setSelected(false);
            motivatCheckBox.setVisible(true);
//            dataMotivareTextField.setVisible(true);
//            addMotivareBtn.setVisible(true);
            formatData2.setVisible(false);
            dataPredareTextField.setVisible(false);
            dataPredareTextField.setText("");
            predareTemaLabel.setVisible(false);
            intarziereCheckBox.setSelected(false);
            intarziereCheckBox.setVisible(true);
//            dataPredareTextField.setVisible(true);
//            addPredareTemaBtn.setVisible(true);
        }
        else{
            initFields();
        }
        profesorTextField.setText("");
        notaTextField.setText("");
        descriereTextArea.setText("");
    }


    private void createStage(Student student,Tema tema,LocalDate data,String profesor,Integer valoare, String feedback) throws IOException {
        Stage dialogStage=new Stage();
        dialogStage.setTitle("Preview");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/PreviewStage.fxml"));
        AnchorPane root=loader.load();

        PreviewStageController previewStageController=loader.getController();
        previewStageController.setService(this.notaService,dialogStage);
        previewStageController.setFields(student,tema,data,profesor,valoare,feedback);

        Scene scene=new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    public void handlePreview(ActionEvent actionEvent) throws IOException {
        //DATA TYPE VALIDATION
        Student student = null; //trebuie verif selection model
        Tema tema = null; //verif selection model
        LocalDate data = null; //verif parse si vid
        String profesor = null; //verif vid
        Integer valoare = null; //verif parse si vid
        Integer penalizare = 0;
        String feedback = null; //verif vid

        Alert alert=new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("O_o");
        String alertText="";

        //student
        if(studentiTableView.getSelectionModel().isEmpty()){
            alertText+="Trebuie sa selectati un student\n";
        }
        else{
            student= studentiTableView.getSelectionModel().getSelectedItem();
        }

        //tema
        if(temeComboBox.getSelectionModel().isEmpty()){
            alertText+="Trebuie sa selectati o tema\n";
        }
        else{
            tema=temeComboBox.getSelectionModel().getSelectedItem();
        }

        //date
        if(intarziereCheckBox.isSelected()){
            if(dataPredareTextField.getText().equals("")){
                alertText+="Data predarii temei este vida\n";
            }
            else {
                try {
                    data = LocalDate.parse(dataPredareTextField.getText(), DATE_TIME_FORMATTER);
                } catch (DateTimeParseException dtpe) {
                    alertText += "Data predarii temei nu respecta formatul cerut\n";
                }
            }
        }
        else{
            data=LocalDate.now();
        }

        //profesor
        if(profesorTextField.getText().equals("")){
            alertText+="Numele profesorului este vid\n";
        }
        else{
            profesor=profesorTextField.getText();
        }

        //valoare
        if(notaTextField.getText().equals("")){
            alertText+="Valoarea notei este vida\n";
        }
        else{
            try{
                valoare=Integer.parseInt(notaTextField.getText());
            } catch (NumberFormatException nfe){
                alertText+="Valoarea notei nu este numar intreg\n";
            }
        }

        //feedback
        if(descriereTextArea.getText().equals("")){
            alertText+="Feedback-ul este vid\n";
        }
        else{
            feedback="\""+descriereTextArea.getText()+"\"";
        }

        //ALERT
        if(alertText.equals("")){
            //penalizare
            penalizare=temaService.determinare_saptamana(data)-tema.getDeadlineWeek(); //penalizare pt data predarii temei
            for (LocalDate motivare:dosarMotivari) {
                Integer saptamanaMotivare=temaService.determinare_saptamana(motivare);
                if(saptamanaMotivare>=tema.getStartWeek() && saptamanaMotivare<=tema.getDeadlineWeek()){
                    penalizare--;
                }
            }
            if(penalizare>2){
                valoare=1;
                feedback+="\n\nS-A DEPASIT NUMARUL INTARZIERILOR\nTEMA NU MAI POATE FI PREDATA";
            }
            else {
                if (penalizare.equals(1)) {
                    feedback += "\n\n" + PENALIZARE1;
                } else if (penalizare.equals(2)) {
                    feedback += "\n\n" + PENALIZARE2;
                }
                valoare-=penalizare;
            }
            //PreviewStage
            createStage(student,tema,data,profesor,valoare,feedback);
        }
        else{
            alert.setContentText(alertText);
            alert.showAndWait();
        }
    }

    public void handleMotivat(ActionEvent actionEvent) {
        if(motivatCheckBox.isSelected()){
            dataMotivareTextField.setVisible(true);
            formatData1.setVisible(true);
        }
        else{
            dataMotivareTextField.setVisible(false);
            dataMotivareTextField.setText("");
            formatData1.setVisible(false);
            addMotivareBtn.setVisible(false);
        }
    }

    public void handleIntarziat(ActionEvent actionEvent) {
        if(intarziereCheckBox.isSelected()){
            dataPredareTextField.setVisible(true);
            formatData2.setVisible(true);
            predareTemaLabel.setVisible(true);
        }
        else{
            dataPredareTextField.setVisible(false);
            dataPredareTextField.setText("");
            formatData2.setVisible(false);
            predareTemaLabel.setVisible(false);
            predareTemaLabel.setText("data vida");
            predareTemaLabel.setTextFill(Paint.valueOf("black"));
        }
    }

    public void handleAddMotivare(ActionEvent actionEvent) {
        LocalDate data=LocalDate.parse(dataMotivareTextField.getText(),DATE_TIME_FORMATTER);
        if(dosarMotivari.contains(data)){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("O_o");
            alert.setContentText("Motivare duplicata");
            alert.showAndWait();
        }
        else{
            dosarMotivari.add(data);
            dataMotivareTextField.setText("");
        }
    }

    @Override
    public void update(TemaChangeEvent temaChangeEvent) {
        this.modelTema.setAll(FXCollections.observableArrayList((ArrayList<Tema>)temaService.serviceFindAll()));

        saptamanaCurenta=temaService.determinare_saptamana(LocalDate.now());
        for(Tema t:temaService.serviceFindAll()){
            if(t.getDeadlineWeek().equals(saptamanaCurenta)){
                this.temaCurenta=t;
                break;
            }
        }
        temeComboBox.getSelectionModel().select(temaCurenta);
    }

    public void handleToRapoarte(ActionEvent actionEvent) throws IOException {
        Stage rapoarteStage=new Stage();
        rapoarteStage.initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/RapoarteStage.fxml"));
        AnchorPane root=loader.load();
        RapoarteStageController rapoarteStageController=loader.getController();

        rapoarteStageController.setService(studentService,temaService,notaService);
        Scene scene=new Scene(root);
        rapoarteStage.setTitle("Rapoarte");
        rapoarteStage.setScene(scene);
        rapoarteStage.show();
    }
}
