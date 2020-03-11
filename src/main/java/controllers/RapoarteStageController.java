package controllers;

import domains.Nota;
import domains.Student;
import domains.Tema;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import services.NotaService;
import services.StudentService;
import services.TemaService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RapoarteStageController {
    private ArrayList<Double> medii=new ArrayList<>();
    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;
    private ObservableList<Student> writeList=FXCollections.observableArrayList();
    @FXML
    private Label showOptionLabel;
    @FXML
    private TableColumn numeColumn;
    @FXML
    private TableColumn prenumeColumn;
    @FXML
    private TableColumn notaColumn;
    @FXML
    private TableView studentiNoteTableView;
    @FXML
    private TextField numeTextField;
    @FXML
    private TextField prenumeTextField;
    @FXML
    private TextField notaTextField;
    @FXML
    private Label notaLabel;
    @FXML
    private Button admisiBtn;
    @FXML
    private Button mediaBtn;
    @FXML
    private Button integralistiBtn;

    public void setService(StudentService studentService,TemaService temaService,NotaService notaService){
        this.studentService=studentService;
        this.temaService=temaService;
        this.notaService=notaService;
        initFields();
        notaLabel.setText(getTemaGrea());
    }

    private String getTemaGrea(){
        String minTema = "-";
        double minMedia=11;
        for(Tema t:temaService.serviceFindAll()) {
            double suma = 0, k = 0;
            for (Student s : studentService.serviceFindAll()) {
                Nota n = notaService.serviceFindOne(s.getId() + " " + t.getId());
                if (n == null) {
                    suma += 1;
                } else {
                    suma += n.getNota();
                }
                k++;
            }
            if (k > 0) {
                if (suma / k < minMedia) {
                    minMedia = suma / k;
                    minTema = t.getId().toString();
                }
            }
        }
        return minTema;
    }

    private void initFields(){
        writeList.setAll(FXCollections.observableArrayList((ArrayList<Student>)studentService.serviceFindAll()));
    }

    private void initColumns(){
        numeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Student,String> cellDataFeatures) {
                return new ReadOnlyObjectWrapper(cellDataFeatures.getValue().getNume());
            }
        });
        prenumeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Student,String> cellDataFeatures) {
                return new ReadOnlyObjectWrapper(cellDataFeatures.getValue().getPrenume());
            }
        });
        notaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue call(TableColumn.CellDataFeatures<Student,String> cellDataFeatures) {
                return new ReadOnlyObjectWrapper(determinareNotaLab(cellDataFeatures.getValue()));
            }
        });
    }

    private Integer getPondereTema(Tema t){
        return t.getDeadlineWeek()-t.getStartWeek();
    }

    private Double determinareNotaLab(Student s){
        double s1,s2;
        s1=0;
        s2=0;
        for(Tema t: temaService.serviceFindAll()){
            Nota n=notaService.serviceFindOne(s.getId()+" "+t.getId());
            if(n==null){
                s1+=1*getPondereTema(t);
            }
            else{
                s1+=n.getNota()*getPondereTema(t);
            }
            s2+=getPondereTema(t);
        }
        medii.add(s1/s2);
        return s1/s2;
    }

    public void initialize(){
        //binding data to tableView
        studentiNoteTableView.setItems(writeList);
        //set cell value factory method
//        numeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue call(TableColumn.CellDataFeatures<Student,String> cellDataFeatures) {
//                return new ReadOnlyObjectWrapper(cellDataFeatures.getValue().getNume());
//            }
//        });
//        prenumeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue call(TableColumn.CellDataFeatures<Student,String> cellDataFeatures) {
//                return new ReadOnlyObjectWrapper(cellDataFeatures.getValue().getPrenume());
//            }
//        });
//        notaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student,String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue call(TableColumn.CellDataFeatures<Student,String> cellDataFeatures) {
//                return new ReadOnlyObjectWrapper(determinareNotaLab(cellDataFeatures.getValue()));
//            }
//        });

        //search nume
        numeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                writeList.setAll((((ArrayList<Student>)studentService.serviceFindAll()).stream().filter(x->{
                    return x.getNume().startsWith(newValue);
                }).collect(Collectors.toList())));
            }
        });
        //search prenume
        prenumeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                writeList.setAll((((ArrayList<Student>)studentService.serviceFindAll()).stream().filter(x->{
                    return x.getPrenume().startsWith(newValue);
                }).collect(Collectors.toList())));
            }
        });
        //search nota
//        notaTextField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
//                writeList.setAll(((ArrayList<Student>)studentService.serviceFindAll()).stream().filter(x->{
//                    return x.getNota().toString().startsWith(newValue);
//                }).collect(Collectors.toList()));
//            }
//        });
    }

    public void handleShowAdmisiLab(ActionEvent actionEvent) {
        showOptionLabel.setText("Studenti admisi la laborator");
        writeList.setAll(((ArrayList<Student>)studentService.serviceFindAll()).stream().filter(s->{
            return determinareNotaLab(s)>=4;
        }).collect(Collectors.toList()));

        initColumns();
    }

    public void handleShowMediaLab(ActionEvent actionEvent) {
        showOptionLabel.setText("Nota laborator studenti");
        initFields();
        initColumns();
    }

    public void handleShowIntegralisti(ActionEvent actionEvent) {
        showOptionLabel.setText("Studentii care au predat toate temele la timp");
        writeList.setAll(((ArrayList<Student>)studentService.serviceFindAll()).stream().filter(s->{
            Boolean integralist=true;
            for(Tema t:temaService.serviceFindAll()){
                Nota n=notaService.serviceFindOne(s.getId()+" "+t.getId());
                if(n==null) {
                    if (t.getDeadlineWeek() < temaService.determinare_saptamana(LocalDate.now())) {
                        integralist = false;
                        break;
                    }
                }
                else{
                    if(t.getDeadlineWeek()<temaService.determinare_saptamana(n.getData())){
                        integralist=false;
                        break;
                    }
                }
            }
            return integralist;
        }).collect(Collectors.toList()));

        initColumns();
    }
}
