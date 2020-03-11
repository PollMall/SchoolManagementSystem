package controllers;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class StartStageController {
    private Stage currentStage;
    private Stage temaStage;
    private Stage notaStage;

    public void setStages(Stage currentStage,Stage temaStage,Stage notaStage){
        this.currentStage=currentStage;
        this.temaStage=temaStage;
        this.notaStage=notaStage;
    }

    public void initialize(){

    }

    public void handleToTeme(ActionEvent actionEvent) {
        currentStage.close();
        temaStage.show();
    }

    public void handleToNote(ActionEvent actionEvent) {
        currentStage.close();
        notaStage.show();
    }
}
