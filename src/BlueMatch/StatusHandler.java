package BlueMatch;

import BlueMatch.model.Datasource;
import BlueMatch.model.Log;
import BlueMatch.model.OverviewRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.tools.Diagnostic;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class StatusHandler {

    private Controller parentController;
    Log log=new Log();
    private Timestamp timestamp;
    private Scene ParentScene;
@FXML
private Button statusaangeboden;
@FXML
    private Button statusuitgenodigd;
    @FXML
    private Button statusafgewezen;

    @FXML
    private void initialize() {
        System.out.println("Initialized");

    }


    public void setParentScene(Scene scene) {
        this.ParentScene = scene;
    }

    public void setParentController(Controller controller) {
        this.parentController = controller;
    }

    public void statusReader(int aanbodID,String type){

    }
    @FXML
    public void modAanbod (){

    }
    @FXML
    public void deleteAanbod (){

    }
    @FXML
    public void statchangeteruggetrokken (ActionEvent event) throws SQLException, IOException {
        log.setNewstatus("Teruggetrokken");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        System.out.println("status added to log");
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(),log.getIdaanbodlog(),log.getIdaanvraaglog());

        changeSceneMain(event);

    }
    public void statchangeafgewezen (ActionEvent event) throws SQLException, IOException {
        log.setNewstatus("Afgewezen");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        System.out.println("status added to log");
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(),log.getIdaanbodlog(),log.getIdaanvraaglog());

        changeSceneMain(event);

    }
    @FXML
    public void statchangeaangeboden (ActionEvent event) throws SQLException, IOException {
        log.setNewstatus("Aangeboden");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        System.out.println("status added to log");
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(),log.getIdaanbodlog(),log.getIdaanvraaglog());

        changeSceneMain(event);

    }
    @FXML
    public void statchangeuitgenodigd (ActionEvent event) throws SQLException, IOException {
        log.setNewstatus("Uitgenodigd voor gesprek");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        System.out.println("status added to log");
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(),log.getIdaanbodlog(),log.getIdaanvraaglog());

        changeSceneMain(event);
    }

    @FXML
    public void statchangegeplaatst (ActionEvent event) throws SQLException, IOException {
        log.setNewstatus("Geplaatst");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        System.out.println("status added to log");
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(),log.getIdaanbodlog(),log.getIdaanvraaglog());
        changeSceneMain(event);

    }
//
//    private void changescreenmn (ActionEvent event){
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene((ParentScene));
//        window.show();
//        parentController.refreshscreen();
//    }

    @FXML
    public void statchangeafronden (ActionEvent event) throws SQLException, IOException {
        log.setNewstatus("Afronden-Onderhandelen");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        System.out.println("status added to log");
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(),log.getIdaanbodlog(),log.getIdaanvraaglog());
        changeSceneMain(event);

    }
public void editStatus(OverviewRecord overviewrecord){

        if (overviewrecord.getStatusaanbod().contentEquals("Aangeboden")){
            statusaangeboden.setStyle("-fx-background-color:  #2ba8cf");
                    statusaangeboden.setTextFill(Color.WHITE);
            System.out.println("color set");
        } else
        {
            System.out.println("color not set");
            System.out.println("status " + overviewrecord.getStatusaanbod());
        }
        log.setIdaanbodlog(overviewrecord.getIdaanbod());
        log.setIdaanvraaglog(overviewrecord.getIdaanvraag());
        log.setOldstatus(overviewrecord.getStatusaanbod());



}
private void setTimestamp(){
    timestamp = new Timestamp((System.currentTimeMillis()));
    log.setTimestamplog(String.valueOf(timestamp));
}



    public void changeSceneMain(ActionEvent event) throws IOException {

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene((ParentScene));
        window.show();
        parentController.refreshscreen();
//        parentController.updateMainView();
        Main.windowWidth = (int) window.getWidth();
    }
}
