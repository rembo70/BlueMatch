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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static BlueMatch.LoginauthController.userEmail;
import static BlueMatch.LoginauthController.userpassword;


public class StatusHandler {
    private OverviewRecord overviewrcrd = new OverviewRecord();
    private Controller parentController;
    private static String mdwEmail;

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

    @FXML
    void gotologin (MouseEvent event) throws IOException {
        System.out.println("goto login screen");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Loginauth.fxml"));
        Parent root = loader.load();
        LoginauthController logincontroller = loader.getController();
        Scene LoginauthScene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene((LoginauthScene));
        window.show();
        window.setHeight(500);
        window.setWidth(360);

    }

    void setParentScene(Scene scene) {
        this.ParentScene = scene;
    }

    void setParentController(Controller controller) {
        this.parentController = controller;
    }

    @FXML
    public void statchangeteruggetrokken(ActionEvent event) throws SQLException {
        log.setNewstatus("Teruggetrokken");
        log.setUserid(userEmail);

        if (Log_Opmerkinguitbreiden()) {
            Optional<String> result = berichtmedewerkerdialogue();
            if (result.isPresent()) {
                String subject = "BM - De status van je aanbieding bij " + overviewrcrd.getRefbroker() + " " + overviewrcrd.getRefklant() + " is gewijzigd naar: 'Teruggetrokken'";
                String messagebody = "De status van je aanbieding bij " + overviewrcrd.getRefbroker() + " " + overviewrcrd.getRefklant() + " is gewijzigd naar: 'Teruggetrokken' <br> <br>";
                //messagebody += "Bereid je goed voor en alvast heel veel succes ! <br> <br>";
                if (!result.get().equals("")) {
                    messagebody += "Opmerking: <br>" + result.get();
                }
                messagebody += "<br> Dit bericht is automatisch gegenereerd vanuit BlueMatch";

                sendmail(overviewrcrd.getMedewerker(), subject, messagebody);
            } else {
                //System.out.println("geen mail verstuurd");
            }
            changeSceneMain(event);
        }
    }
    @FXML
    public void statchangecold(ActionEvent event) throws SQLException {
        log.setNewstatus("Cold");
        log.setUserid(userEmail);

        if (Log_Opmerkinguitbreiden()) {
            Optional<String> result = berichtmedewerkerdialogue();
            if (result.isPresent()) {
                String subject = "BM - De status van je aanbieding bij " + overviewrcrd.getRefbroker() + " " + overviewrcrd.getRefklant() + " is gewijzigd naar: 'Cold'";
                String messagebody = "De status van je aanbieding bij " + overviewrcrd.getRefbroker() + " " + overviewrcrd.getRefklant() + " is gewijzigd naar: 'Cold' <br> <br>";
                //messagebody += "Bereid je goed voor en alvast heel veel succes ! <br> <br>";
                if (!result.get().equals("")) {
                    messagebody += "Opmerking: <br>" + result.get();
                }
                messagebody += "<br> Dit bericht is automatisch gegenereerd vanuit BlueMatch";

                sendmail(overviewrcrd.getMedewerker(), subject, messagebody);
            } else {
                //System.out.println("geen mail verstuurd");
            }
            changeSceneMain(event);
        }
    }
    public void statchangeafgewezen(ActionEvent event) throws SQLException {
        log.setNewstatus("Afgewezen");
        log.setUserid(userEmail);
        if(Log_Opmerkinguitbreiden()){
            Optional<String> result = berichtmedewerkerdialogue();
            if (result.isPresent()) {
                String subject = "BM - Helaas, een afgewijzing voor de functie bij" + overviewrcrd.getRefklant() + " " + overviewrcrd.getRefbroker();
                String messagebody = "De status van je aanbieding bij " + overviewrcrd.getRefbroker() + " " + overviewrcrd.getRefklant() + " is gewijzigd naar: 'Afgewezen' <br> <br>";
                messagebody += "Volgende keer beter ! <br> <br>";
                messagebody=setMessageBody(overviewrcrd, messagebody);
                if (!result.get().equals("")) {
                    messagebody += "Opmerking: <br>" + result.get();
                }
                messagebody += "<br> Dit bericht is automatisch gegenereerd vanuit BlueMatch";

                sendmail(overviewrcrd.getMedewerker(), subject, messagebody);
            } else {
                //System.out.println("geen mail verstuurd");
            }
            changeSceneMain(event);
        }
    }


    boolean Log_Opmerkinguitbreiden() throws SQLException {
        Optional<String> result = showopmerkingdialogue(log.getNewstatus());
        String newopmerking;
        if (result.isPresent()) {
            setTimestamp();
            if (result.get().length() >= 1) {
                newopmerking = overviewrcrd.getOpmerkingaanbod() + "\n" + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " " + result.get();
            } else {
                //System.out.println("veld is leeg" + result.get());
                newopmerking = overviewrcrd.getOpmerkingaanbod();
            }
            Datasource.getInstance().logToevoegen(log);
            Datasource.getInstance().updateAanbodStatus(log.getNewstatus(), newopmerking, log.getIdaanbodlog(), log.getIdaanvraaglog());
            return true;
        }
        return false;
    }

    @FXML
    public void statchangeaangeboden(ActionEvent event) throws SQLException {
        log.setNewstatus("Aangeboden");
        log.setUserid(userEmail);
        if(Log_Opmerkinguitbreiden()){
            Optional<String> result = berichtmedewerkerdialogue();
            if (result.isPresent()) {
                String subject = "BM - Je bent aangeboden bij " + overviewrcrd.getRefklant() + " " + overviewrcrd.getRefbroker();
                String messagebody = "De status van je aanbieding bij " + overviewrcrd.getRefbroker() + " " + overviewrcrd.getRefklant() + " is gewijzigd naar: 'Aangeboden' <br> <br>";
                messagebody += "We laten het weten zodra we meer horen  <br> <br>";
                messagebody=setMessageBody(overviewrcrd, messagebody);
                if (!result.get().equals("")) {
                    messagebody += "Opmerking: <br>" + result.get();
                }
                messagebody += "<br> Dit bericht is automatisch gegenereerd vanuit BlueMatch";

                sendmail(overviewrcrd.getMedewerker(), subject, messagebody);
            } else {
                //System.out.println("geen mail verstuurd");
            }
            changeSceneMain(event);
        }

    }
public String setMessageBody(OverviewRecord overviewrcrd, String messagebody){

    messagebody += "Details: <br>";
    messagebody += "functie: " + overviewrcrd.getFunctie() + "<br>";
    messagebody += "contactpersoon bij klant: " + overviewrcrd.getRefcontact() + "<br>";
    messagebody += "uren per week gevraagd: " + overviewrcrd.getVraagurenweek() + "<br>";
    messagebody += "uren per week aangeboden: " + overviewrcrd.getUrenperweekaanbod() + "<br>";
    messagebody += "locatie: " + overviewrcrd.getLocatie() + "<br>";
    messagebody += "startdatum gevraagd: " + overviewrcrd.getStartdatum()+ "<br>";
    messagebody += "link naar aanvraag: " + overviewrcrd.getLinkaanvraag() + "<br>";
    messagebody += "Tariefaanvraag: " + overviewrcrd.getTariefaanvraag() + "<br>";
    messagebody += "Tariefaanbieding: " + overviewrcrd.getTariefaanbod() + "<br>";
    messagebody += "Datumaanbieding: " + overviewrcrd.getDatumaanbieding() + "<br><br>";
    return messagebody;
}

    @FXML
    public void statchangeuitgenodigd(ActionEvent event) throws SQLException {
        log.setNewstatus("Uitgenodigd voor gesprek");
        log.setUserid(userEmail);

        if (Log_Opmerkinguitbreiden()) {
            Optional<String> result = berichtmedewerkerdialogue();
            if (result.isPresent()) {
                String subject = "BM - Je bent uitgenodigd voor een gesprek bij " + overviewrcrd.getRefklant() + " " + overviewrcrd.getRefbroker();
                String messagebody = "Goed nieuws, De status van je aanbieding bij " + overviewrcrd.getRefbroker() + " " + overviewrcrd.getRefklant() + " is gewijzigd naar: 'Uitgenodigd voor gesprek' <br> <br>";
                messagebody += "Bereid je goed voor en alvast heel veel succes ! <br> <br>";
                messagebody=setMessageBody(overviewrcrd, messagebody);
                if (!result.get().equals("")) {
                    messagebody += "Opmerking: <br>" + result.get();
                }
                messagebody += "<br> Dit bericht is automatisch gegenereerd vanuit BlueMatch";

                sendmail(overviewrcrd.getMedewerker(), subject, messagebody);
            } else {
                //System.out.println("geen mail verstuurd");
            }
            changeSceneMain(event);
        }
    }


    public static void sendmail(String medewerkernaam, String subject, String messagebody){

        mdwEmail = Datasource.getInstance().queryMedewerkeremail(medewerkernaam).getEmailmedewerker();
        System.out.println("Email: "  + mdwEmail);
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


    private static PasswordField getPassword(){
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

    private Optional<String>  berichtmedewerkerdialogue() {
        TextInputDialog dialog = new TextInputDialog("");

        dialog.setTitle("Bericht naar medewerker");
        dialog.setHeaderText("Wil je een bericht naar de medewerker sturen ? \nSelecteer 'Cancel' indien je geen bericht naar de medewerker wil sturen. ");
        dialog.setContentText("(Optioneel) Opmerking bij aanbod: \n");

        TextArea opmerkingmdw = new TextArea();
        opmerkingmdw.setEditable(true);
        opmerkingmdw.setWrapText(true);

        opmerkingmdw.setMaxWidth(Double.MAX_VALUE);
        opmerkingmdw.setMaxHeight(Double.MAX_VALUE);

        dialog.getDialogPane().setExpandableContent(opmerkingmdw);
          Optional<String> result = dialog.showAndWait();
        if (opmerkingmdw!=null&&!opmerkingmdw.getText().equals("")){
            result = result.of(result.get() + " <br> " + opmerkingmdw.getText() + "<br><br>");
        }
        return result;
    }

    @FXML
    public void statchangegeplaatst(ActionEvent event) throws SQLException {
        log.setNewstatus("Geplaatst");
        log.setUserid(userEmail);
        if(Log_Opmerkinguitbreiden()){
            Optional<String> result = berichtmedewerkerdialogue();
            if (result.isPresent()) {
                String subject = "BM - Je bent geplaatst bij " + overviewrcrd.getRefklant() + " " + overviewrcrd.getRefbroker();
                String messagebody = "De status van je aanbieding bij " + overviewrcrd.getRefbroker() + " " + overviewrcrd.getRefklant() + " is gewijzigd naar: 'Geplaatst' <br> <br>";
                messagebody += "Heel veel succes met je opdracht !  <br> <br>";
                messagebody=setMessageBody(overviewrcrd, messagebody);
                if (!result.get().equals("")) {
                    messagebody += "Opmerking: <br>" + result.get();
                }
                messagebody += "<br> Dit bericht is automatisch gegenereerd vanuit BlueMatch";

                sendmail(overviewrcrd.getMedewerker(), subject, messagebody);
            } else {
                //System.out.println("geen mail verstuurd");
            }
            changeSceneMain(event);
        }
    }

    @FXML
    public void statchangeafronden(ActionEvent event) throws SQLException {
        log.setNewstatus("Afronden-Onderhandelen");
        log.setUserid(userEmail);
        if(Log_Opmerkinguitbreiden()){
            Optional<String> result = berichtmedewerkerdialogue();
            if (result.isPresent()) {
                String subject = "BM - Je bent weer een stapje dichter bij een plaatsing bij " + overviewrcrd.getRefklant() + " " + overviewrcrd.getRefbroker();
                String messagebody = "De status van je aanbieding bij " + overviewrcrd.getRefbroker() + " " + overviewrcrd.getRefklant() + " is gewijzigd naar: 'Afronden / onderhandelen' <br> <br>";
                messagebody += "Nog eventjes geduld !  <br> <br>";
                messagebody=setMessageBody(overviewrcrd, messagebody);
                if (!result.get().equals("")) {
                    messagebody += "Opmerking: <br>" + result.get();
                }
                messagebody += "<br> Dit bericht is automatisch gegenereerd vanuit BlueMatch";

                sendmail(overviewrcrd.getMedewerker(), subject, messagebody);
            } else {
                //System.out.println("geen mail verstuurd");
            }
            changeSceneMain(event);
        }
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
