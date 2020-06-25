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
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.tools.Diagnostic;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;


public class StatusHandler {
    private OverviewRecord overviewrcrd = new OverviewRecord();
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
    private Button statusgeplaatst;
    @FXML
    private Button statusafronden;
    @FXML
    private Button statusteruggetrokken;
    @FXML
    private Label Brokerlabel;
    @FXML
    private Label Klantlabel;
    @FXML
    private Label Medewerkerlabel;
    @FXML
    private Label Huidigestatuslabel;
    @FXML
    private void initialize() {
        //System.out.println("Initialized");

    }


    void setParentScene(Scene scene) {
        this.ParentScene = scene;
    }

    void setParentController(Controller controller) {
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
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(),"", log.getIdaanbodlog(),log.getIdaanvraaglog());

        changeSceneMain(event);

    }
    public void statchangeafgewezen (ActionEvent event) throws SQLException, IOException {
        log.setNewstatus("Afgewezen");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(),"",log.getIdaanbodlog(),log.getIdaanvraaglog());

        changeSceneMain(event);

    }
    @FXML
    public void statchangeaangeboden (ActionEvent event) throws SQLException, IOException {
        log.setNewstatus("Aangeboden");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(),"", log.getIdaanbodlog(),log.getIdaanvraaglog());

        changeSceneMain(event);

    }
    @FXML
    public void statchangeuitgenodigd (ActionEvent event) throws SQLException, IOException {
        log.setNewstatus("Uitgenodigd voor gesprek");


        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Weet je zeker dat je de status wil aanpassen naar uitgenodigd ?");
        dialog.setTitle("");
        dialog.setHeaderText("Hoe ga je  op deze aanvraag aanbieden ");
        dialog.setContentText("Volgende tekst toegen aan de opmerking bij het aanbod:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
                        setTimestamp();
            String newopmerking =  overviewrcrd.getOpmerkingaanbod() + "\n" +  java.time.LocalDate.now() + " " + result.get();
            System.out.println(overviewrcrd.getOpmerkingaanbod());
            System.out.println(newopmerking);
            Datasource.getInstance().logToevoegen(log);
            Datasource.getInstance().updateAanbodStatus(log.getNewstatus(), newopmerking, log.getIdaanbodlog(),log.getIdaanvraaglog());

            changeSceneMain(event);
        }

// Traditional way to get the response value.
       // Optional<String> result2 = dialog.showAndWait();


    }

    @FXML
    public void statchangegeplaatst (ActionEvent event) throws SQLException, IOException {
        log.setNewstatus("Geplaatst");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        System.out.println("status added to log");
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(),"",log.getIdaanbodlog(),log.getIdaanvraaglog());
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
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(),"",log.getIdaanbodlog(),log.getIdaanvraaglog());
        changeSceneMain(event);

    }
void editStatus(OverviewRecord overviewrecord){

    overviewrcrd = overviewrecord;
    switch (overviewrecord.getStatusaanbod()) {
        case "Aangeboden":
            statusaangeboden.setStyle("-fx-background-color:  #2ba8cf");
            statusaangeboden.setTextFill(Color.WHITE);
            break;
        case "Uitgenodigd voor gesprek":
            statusuitgenodigd.setStyle("-fx-background-color:  #2ba8cf");
            statusuitgenodigd.setTextFill(Color.WHITE);
            break;
        case "Afronden-Onderhandelen":
            statusafronden.setStyle("-fx-background-color:  #2ba8cf");
            statusafronden.setTextFill(Color.WHITE);
            break;
        case "Geplaatst":
            statusgeplaatst.setStyle("-fx-background-color:  #2ba8cf");
            statusgeplaatst.setTextFill(Color.WHITE);
            break;
        case "Afgewezen":
            statusafgewezen.setStyle("-fx-background-color:  #2ba8cf");
            statusafgewezen.setTextFill(Color.WHITE);
            break;
        case "Teruggetrokken":
            statusteruggetrokken.setStyle("-fx-background-color:  #2ba8cf");
            statusteruggetrokken.setTextFill(Color.WHITE);
            break;


    }

        log.setIdaanbodlog(overviewrecord.getIdaanbod());
        log.setIdaanvraaglog(overviewrecord.getIdaanvraag());
        log.setOldstatus(overviewrecord.getStatusaanbod());
        Brokerlabel.setText(overviewrecord.getRefbroker());
        Klantlabel.setText(overviewrecord.getRefklant());
        Medewerkerlabel.setText(overviewrecord.getMedewerker());
        Huidigestatuslabel.setText(overviewrecord.getStatusaanbod());
}
private void setTimestamp(){
    timestamp = new Timestamp((System.currentTimeMillis()));
    log.setTimestamplog(String.valueOf(timestamp));
}



    public void changeSceneMain(ActionEvent event) {

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene((ParentScene));
        window.show();
        parentController.refreshscreen();
//        parentController.updateMainView();
        Main.windowWidth = (int) window.getWidth();
    }
}
