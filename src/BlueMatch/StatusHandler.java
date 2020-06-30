package BlueMatch;

import BlueMatch.model.Datasource;
import BlueMatch.model.Log;
import BlueMatch.model.Medewerker;
import BlueMatch.model.OverviewRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.tools.Diagnostic;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import static BlueMatch.LoginauthController.userEmail;
import static BlueMatch.LoginauthController.userpassword;


public class StatusHandler {
    private OverviewRecord overviewrcrd = new OverviewRecord();
    private Controller parentController;
    private String mdwEmail;

    private Log log = new Log();
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
    private Object PasswordField;
    @FXML
    private Label statuslogin;

    @FXML
    private void initialize() {
        switch (LoginauthController.Passwrdstatus){
            case "Not Validated":
                statuslogin.setText ("Email not validated");
                statuslogin.setTextFill(Color.GREY);

            break;
            case "OK":
                statuslogin.setText ("Connected");
                statuslogin.setTextFill(Color.GREEN);
                break;
            case "NOK":
                statuslogin.setText ("Not connected");
                statuslogin.setTextFill(Color.RED);
                break;
            default:
                statuslogin.setText ("Connectiestatus onbekend");
                statuslogin.setTextFill(Color.GREY);
        }
    }


    void setParentScene(Scene scene) {
        this.ParentScene = scene;
    }

    void setParentController(Controller controller) {
        this.parentController = controller;
    }

    @FXML
    public void modAanbod() {

    }

    @FXML
    public void deleteAanbod() {

    }

    @FXML
    public void statchangeteruggetrokken(ActionEvent event) throws SQLException {
        log.setNewstatus("Teruggetrokken");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(), "", log.getIdaanbodlog(), log.getIdaanvraaglog());

        changeSceneMain(event);

    }

    public void statchangeafgewezen(ActionEvent event) throws SQLException {
        log.setNewstatus("Afgewezen");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(), "", log.getIdaanbodlog(), log.getIdaanvraaglog());

        changeSceneMain(event);

    }

    @FXML
    public void statchangeaangeboden(ActionEvent event) throws SQLException {
        log.setNewstatus("Aangeboden");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(), "", log.getIdaanbodlog(), log.getIdaanvraaglog());

        changeSceneMain(event);

    }

    @FXML
    public void statchangeuitgenodigd(ActionEvent event) throws SQLException {
        log.setNewstatus("Uitgenodigd voor gesprek");

        Optional<String> result = showopmerkingdialogue(log.getNewstatus());
        String newopmerking;
        if (result.isPresent()) {
            System.out.println("result is present" + result.get());
            setTimestamp();
            if (result.get().length() >= 1) {
                newopmerking = overviewrcrd.getOpmerkingaanbod() + "\n" + java.time.LocalDate.now() + " " + result.get();
            } else {
                System.out.println("veld is leeg" + result.get() + "nn");
                newopmerking = overviewrcrd.getOpmerkingaanbod();
            }

            Datasource.getInstance().logToevoegen(log);
            Datasource.getInstance().updateAanbodStatus(log.getNewstatus(), newopmerking, log.getIdaanbodlog(), log.getIdaanvraaglog());

            String subject = "je bent uitgenodigd voor een gesprek bij " + overviewrcrd.getRefklant() + " " + overviewrcrd.getRefbroker();
            System.out.println("ik ga mail versturen");

            String messagebody = "De status van je aanbieding bij "+ overviewrcrd.getRefbroker() + " " + overviewrcrd.getRefklant() + " is gewijzigd naar: 'Uitgenodigd voor gesprek <br>";
            messagebody += "Bereid je goed voor en alvast heel veel succes ! <br> <br>";
            messagebody += "Dit bericht is automatisch gegenereerd vanuit BlueMatch";

            sendmail(overviewrcrd.getMedewerker(), subject, messagebody);
            changeSceneMain(event);
        }
    }

    private void sendmail(String medewerkernaam, String subject, String messagebody){

        mdwEmail = Datasource.getInstance().queryMedewerkeremail(medewerkernaam).getEmailmedewerker();
        //System.out.println("Email: "  + mdwEmail);
        String destination = mdwEmail;
        String from = LoginauthController.userEmail;
        System.out.println("send mail from" + from + " ww "  + " to " + destination);
        if (LoginauthController.userpassword==null){
            LoginauthController.userpassword=getPassword();
        }
        PasswordField password=LoginauthController.userpassword;
        System.out.println(destination + messagebody);
        if (password!=null){
            System.out.println("Mail wordt gestuurd");
        new SendEmailOffice365().sendEmail(from, password,destination, subject, messagebody);} else
            System.out.println("mail niet gestuurd");
    }


    private PasswordField getPassword(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Geef je wachtwoord voor mail");
        dialog.setHeaderText("");
        dialog.setGraphic(new Circle(15, Color.RED)); // Custom graphic
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        PasswordField pwd = new PasswordField();
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);
        content.getChildren().addAll(new Label("Geef je wachtwoord:"), pwd);
        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return pwd.getText();
            }
            System.out.println("no password detected");
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
          return pwd;
        }
        System.out.println("no password detected");
        return null;
    }

    private Optional<String> showopmerkingdialogue(String newstatus) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Bevestiging");
        dialog.setHeaderText("Weet je zeker dat je de status wil aanpassen naar uitgenodigd ?");
        dialog.setContentText("Opmerking bij aanbod:");

        Optional<String> result = dialog.showAndWait();
        return result;
    }

    @FXML
    public void statchangegeplaatst(ActionEvent event) throws SQLException {
        log.setNewstatus("Geplaatst");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        System.out.println("status added to log");
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(), "", log.getIdaanbodlog(), log.getIdaanvraaglog());
        changeSceneMain(event);

    }

    @FXML
    public void statchangeafronden(ActionEvent event) throws SQLException {
        log.setNewstatus("Afronden-Onderhandelen");
        setTimestamp();
        Datasource.getInstance().logToevoegen(log);
        System.out.println("status added to log");
        Datasource.getInstance().updateAanbodStatus(log.getNewstatus(), "", log.getIdaanbodlog(), log.getIdaanvraaglog());
        changeSceneMain(event);

    }

    void editStatus(OverviewRecord overviewrecord) {

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

    private void setTimestamp() {
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
