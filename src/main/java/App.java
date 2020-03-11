import controllers.NotaStageController;
import controllers.StartStageController;
import controllers.TemaStageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import repositories.NotaXmlRepository;
import repositories.StudentXmlRepository;
import repositories.TemaXmlRepository;
import services.NotaService;
import services.StudentService;
import services.TemaService;
import utils.structures.StructuraAnUniversitar;

import java.io.File;

public class App extends Application {
    private StudentService studentService = new StudentService(new StudentXmlRepository(new File("src/main/resources/studenti.xml")));
    private TemaService temaService = new TemaService(new TemaXmlRepository(new File("src/main/resources/teme.xml")));
    private NotaService notaService = new NotaService(new NotaXmlRepository(new File("src/main/resources/note.xml"),studentService.getStudentRepository(),temaService.getTemaRepository(),new StructuraAnUniversitar()),studentService.getStudentRepository());

    @Override
    public void start(Stage stage) throws Exception {
        //start
        FXMLLoader loaderStart=new FXMLLoader();
        loaderStart.setLocation(getClass().getResource("/views/StartStage.fxml"));
        AnchorPane root= loaderStart.load();
        StartStageController startStageController=loaderStart.getController();
        Scene startScene=new Scene(root);
        stage.setTitle("Management System");
        stage.setScene(startScene);

        //teme
        FXMLLoader loaderTema=new FXMLLoader();
        loaderTema.setLocation(getClass().getResource("/views/TemaStage.fxml"));
        AnchorPane temeRoot= loaderTema.load();
        TemaStageController temaStageController=loaderTema.getController();
        Scene temaScene=new Scene(temeRoot);

        Stage temaStage=new Stage();
        temaStage.initModality(Modality.WINDOW_MODAL);
        temaStage.setTitle("Teme Management System");
        temaStage.setScene(temaScene);

        //note
        FXMLLoader loaderNota=new FXMLLoader();
        loaderNota.setLocation(getClass().getResource("/views/NotaStage.fxml"));
        AnchorPane notaRoot=loaderNota.load();
        NotaStageController notaStageController=loaderNota.getController();
        Scene notaScene=new Scene(notaRoot);

        Stage notaStage=new Stage();
        notaStage.initModality(Modality.WINDOW_MODAL);
        notaStage.setTitle("Nota Management System");
        notaStage.setScene(notaScene);

        //start
        temaStageController.setService(temaService,temaStage,notaStage);
        notaStageController.setService(studentService,temaService,notaService,temaStage,notaStage);
        startStageController.setStages(stage,temaStage,notaStage);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
