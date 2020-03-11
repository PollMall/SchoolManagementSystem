package controllers;

import domains.Student;
import domains.Tema;
import exceptions.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repositories.InMemoryRepository;
import repositories.TemaRepository;
import services.TemaService;

public class ModifyController {
    private Tema tema;
    private Stage dialogStage;
    private TemaService service;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField descriereTextField;
    @FXML
    private TextField startWeekTextField;
    @FXML
    private TextField deadlineWeekTextField;
    @FXML
    private Button modifyBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Button cancelBtn;

    public void setService(TemaService service, Stage dialogStage,Tema tema) {
        this.service = service;
        this.dialogStage = dialogStage;
        this.tema=tema;
        setInit(tema);
    }

    public void setInit(Tema tema){
        idTextField.setEditable(false);
        if(tema!=null) {
            idTextField.setText(tema.getId().toString());
            descriereTextField.setText(tema.getDescriere());
            startWeekTextField.setText(tema.getStartWeek().toString());
            deadlineWeekTextField.setText(tema.getDeadlineWeek().toString());
        }
        else{
            idTextField.setText(String.valueOf(TemaRepository.INDEX+1));
        }
    }

    @FXML
    public void initialize() {
    }

//    @FXML
//    public void save(ActionEvent actionEvent) {
//        Integer id = null;
//        String descriere;
//        Integer startWeek = null;
//        Integer deadlineWeek = null;
//        String alertText = "";
//
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setHeaderText("WARNING");
//
//        try {
//            if (!idTextField.getText().equals(""))
//                id = Integer.parseInt(idTextField.getText());
//            else
//                alertText += "ID null\n";
//        } catch (NumberFormatException nfe) {
//            alertText += "ID-ul trebuie sa fie int!\n";
//        }
//
//        descriere = descriereTextField.getText();
//
//        try {
//            if (startWeekTextField.getText().equals(""))
//                alertText += "StartWeek null\n";
//            else
//                startWeek = Integer.parseInt(startWeekTextField.getText());
//        } catch (NumberFormatException nfe) {
//            alertText += "StartWeek trebuie sa fie int!\n";
//        }
//
//
//        try {
//            if (deadlineWeekTextField.getText().equals(""))
//                alertText += "DeadlineWeek null\n";
//            else
//                deadlineWeek = Integer.parseInt(deadlineWeekTextField.getText());
//        } catch (NumberFormatException nfe) {
//            alertText += "DeadlineWeek trebuie sa fie int!\n";
//        }
//
//        if (!alertText.equals("")) {
//            alert.setContentText(alertText);
//            alert.showAndWait();
//        } else {
//            Tema tema = new Tema(id, descriere, startWeek, deadlineWeek);
//            Tema returnObj = null;
//
//            try {
//                returnObj = service.serviceSave(tema);
//            } catch (ValidationException ve) {
//                alertText += ve.getMesaj();
//            }
//            if (!alertText.equals("")) {
//                alert.setContentText(alertText);
//                alert.showAndWait();
//            } else {
//                if (returnObj!=null){
//                    alert.setContentText("ID-ul exista deja");
//                    alert.setHeaderText("O_o");
//                    alert.showAndWait();
//                }
//                else{
//                    Alert successAlert=new Alert(Alert.AlertType.INFORMATION);
//                    successAlert.setContentText("Tema salvata cu succes");
//                    successAlert.setHeaderText("Succes");
//                    successAlert.showAndWait();
//                }
//            }
//        }

//        Tema tema = new Tema(id, descriere, startWeek, deadlineWeek);
//        Tema returnObj = null;
//        try {
//            returnObj = service.serviceSave(tema);
//        } catch (ValidationException ve) {
//            alertText += ve.getMesaj();
//        }
//
//        if (!alertText.equals("")) {
//            alert.setContentText(alertText);
//            alert.showAndWait();
//        } else {
//            dialogStage.close();
//        }
//    }

    @FXML
    public void handleClear(ActionEvent actionEvent) {
        if(tema==null) {
            //idTextField.setText(String.valueOf(TemaRepository.INDEX+1));
            descriereTextField.setText("");
            startWeekTextField.setText("");
            deadlineWeekTextField.setText("");
        }
        else{
            idTextField.setText(tema.getId().toString());
            descriereTextField.setText(tema.getDescriere());
            startWeekTextField.setText(tema.getStartWeek().toString());
            deadlineWeekTextField.setText(tema.getDeadlineWeek().toString());
        }
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleModify(ActionEvent actionEvent) {
        Integer id = null;
        String descriere;
        Integer startWeek = null;
        Integer deadlineWeek = null;
        String alertText = "";

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("WARNING");

        try {
            if (!idTextField.getText().equals(""))
                id = Integer.parseInt(idTextField.getText());
            else
                alertText += "ID null\n";
        } catch (NumberFormatException nfe) {
            alertText += "ID-ul trebuie sa fie int!\n";
        }

        descriere = descriereTextField.getText();

        try {
            if (startWeekTextField.getText().equals(""))
                alertText += "StartWeek null\n";
            else
                startWeek = Integer.parseInt(startWeekTextField.getText());
        } catch (NumberFormatException nfe) {
            alertText += "StartWeek trebuie sa fie int!\n";
        }


        try {
            if (deadlineWeekTextField.getText().equals(""))
                alertText += "DeadlineWeek null\n";
            else
                deadlineWeek = Integer.parseInt(deadlineWeekTextField.getText());
        } catch (NumberFormatException nfe) {
            alertText += "DeadlineWeek trebuie sa fie int!\n";
        }

        if (!alertText.equals("")) {
            alert.setContentText(alertText);
            alert.showAndWait();
        } else {
            Tema newTema = new Tema(id, descriere, startWeek, deadlineWeek);
            Tema returnObj = null;
            try {
                if(tema==null) //save
                    returnObj = service.serviceSave(newTema);
                else //update
                    returnObj=service.serviceUpdate(newTema);
            } catch (ValidationException ve) {
                alertText += ve.getMesaj();
            }
            if (!alertText.equals("")) {
                alert.setContentText(alertText);
                alert.showAndWait();
            } else {
                if (returnObj != null) {
                    alert.setContentText("ID-ul exista deja");
                    alert.setHeaderText("O_o");
                    alert.showAndWait();
                } else {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setContentText("Tema salvata cu succes");
                    successAlert.setHeaderText("^_^");
                    this.dialogStage.close();
                    successAlert.showAndWait();
                }
            }
        }
    }
}
