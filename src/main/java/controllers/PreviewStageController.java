package controllers;

import domains.Nota;
import domains.Student;
import domains.Tema;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import services.NotaService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PreviewStageController {
    private NotaService notaService;
    private Student student;
    private Tema tema;
    private LocalDate data;
    private String profesor;
    private Integer valoare;
    private String feedback;
    private Stage stage;
    @FXML
    private TextArea feedbackTextArea;
    @FXML
    private Label studentPreview;
    @FXML
    private Label temaPreview;
    @FXML
    private Label dataPreview;
    @FXML
    private Label profesorPreview;
    @FXML
    private Label notaPreview;

    public void setService(NotaService notaService,Stage stage){
        this.notaService=notaService;
        this.stage=stage;
    }

    public void setFields(Student student,Tema tema,LocalDate data,String profesor,Integer valoare,String feedback){
        this.student=student;
        this.tema=tema;
        this.data=data;
        this.profesor=profesor;
        this.valoare=valoare;
        this.feedback=feedback;

        studentPreview.setText(student.getNume()+" "+student.getPrenume());
        temaPreview.setText(tema.getId().toString());
        dataPreview.setText(data.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        profesorPreview.setText(profesor);
        notaPreview.setText(valoare.toString());
        feedbackTextArea.setText(feedback);
    }

    public void initialize(){
    }

    public void handleCancel(ActionEvent actionEvent) {
        stage.close();
    }

    public void handleSave(ActionEvent actionEvent){
        Nota nota=new Nota(student.getId(),tema.getId(),data,profesor,valoare,feedback);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(notaService.serviceSave(nota)==null) {
            alert.setHeaderText("^_^");
            alert.setContentText("Nota salvata cu succes");

            stage.close();
        }
        else{
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setHeaderText("O_o");
            alert.setContentText("Exista deja o nota");
        }
        alert.showAndWait();
    }
}
