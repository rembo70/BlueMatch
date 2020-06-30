package BlueMatch;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginauthController {
    @FXML
    private TextField usermailadrfield;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private Button btninloggen;
    @FXML
    private Button buttonok;
    @FXML
    private Label statusvalidatie;
    @FXML
    private Label melding;
    static String userEmail;
    static PasswordField userpassword;
    static String Passwrdstatus;


    @FXML
    public void buttonok (ActionEvent event) throws IOException {
        System.out.println("ok pressed");
        userEmail=usermailadrfield.getText();
        userpassword= passwordfield;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("BlueMatch.FXML"));
        Parent ControllerParent = loader.load();
        Controller controller = loader.getController();
        Scene ControllerScene = new Scene(ControllerParent);
        controller.listOverviewRecord();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setParentScene(window.getScene());
        controller.setParentController(this);
        window.setScene((ControllerScene));
        window.setWidth(1024);
        window.setHeight(700);
        window.show();
    }
    @FXML
    public void passwordprovided (javafx.event.ActionEvent event){
        statusvalidatie.setVisible(false);
        buttonok.setText("Ga verder zonder validatie");
        Passwrdstatus="Not validated";

        buttonok.setDisable(false);
    }

    @FXML
    public Button btnlogin (javafx.event.ActionEvent event) {

        System.out.println("validating email");
        if(usermailadrfield.getText().equals("")||usermailadrfield==null) {
            //System.out.println("geen mailadres");
            statusvalidatie.setTextFill(Color.RED);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("");
            alert.setContentText("Sorry je mail adres is niet ingevuld of leeg, er moet een email adres zijn ingevuld voor je verder kan");
            buttonok.setDisable(true);
            //buttonok.setText("Ga verder zonder validatie");

            alert.showAndWait();
        }
        else if (passwordfield.getText() != null && passwordfield.getText()!="" && !passwordfield.getText().isEmpty()) {
            if (new SendEmailOffice365().sendEmail(usermailadrfield.getText(), passwordfield, usermailadrfield.getText(), "Melding Validate: Ingelogd onder je gebruikersnaam", "Het versturen van een email vanuit jouw account is geslaagd. <br>  Je kan deze mail negeren, indien je zelf de validatie hebt uitgevoerd.") == true) {


                userpassword = passwordfield;
                userEmail = usermailadrfield.getText();
                statusvalidatie.setTextFill(Color.GREEN);
                statusvalidatie.setTextAlignment(TextAlignment.CENTER);
                statusvalidatie.setAlignment(Pos.CENTER);

                statusvalidatie.setText("Validatie geslaagd. Je kan verder gaan");
                statusvalidatie.setVisible(true);
                melding.setVisible(false);
                buttonok.setText("Ga verder");
                Passwrdstatus="OK";
                melding.setDisable(true);
                buttonok.setDisable(false);
            } else {
                System.out.println("Mail not successfully sent");

                statusvalidatie.setText("Validatie niet geslaagd. \n \n Indien je geen password meegeeft kunnen geen \n automatische mails worden gestuurd ");
                statusvalidatie.setTextFill(Color.RED);
                statusvalidatie.setTextAlignment(TextAlignment.CENTER);
                statusvalidatie.setAlignment(Pos.CENTER);
                buttonok.setText("Skip (geen automatische mails)");
                buttonok.setDisable(false);
                Passwrdstatus="NOK";
                melding.setVisible(false);
            }
        }
        else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Warning Dialog");
//            alert.setHeaderText("");
//            alert.setContentText("Je wachtwoord is niet ingevuld, zonder wachtwoord kunnen geen statuswijzigingen vanuit BlueMatch worden doorgevoerd");
            statusvalidatie.setTextFill(Color.ORANGE);
            statusvalidatie.setTextAlignment(TextAlignment.CENTER);
            statusvalidatie.setAlignment(Pos.CENTER);
            statusvalidatie.setText("Geen wachtwoord ingevuld");
            buttonok.setDisable(false);
            buttonok.setText("Skip (geen automatische mails)");
            Passwrdstatus="NOK";
            melding.setTextFill(Color.RED);
        }
        return null;
    }

//    public Button btnlogin(javafx.event.ActionEvent event) {
//    }

//    public Klant getNewKlant() {
//        String klantnaam = klantnaamField.getText();
//        String klantcontactpersoon = klantcontactpersoonField.getText();
//        String klantcontacttelnr = klantcontacttelnrField.getText();
//        String klantcontactemail = klantcontactemailField.getText();
//        String klantopmerking = klantopmerkingField.getText();
//
//
//        Klant newKlant = new Klant();
//        newKlant.setKlantnaam(klantnaam);
//        newKlant.setKlantcontactpersoon(klantcontactpersoon);
//        newKlant.setKlantcontacttelnr(klantcontacttelnr);
//        newKlant.setKlantcontactemail(klantcontactemail);
//        newKlant.setKlantopmerking(klantopmerking);
//
//        return newKlant;
//    }
//
//
//    public void editKlant(Klant klant, String type) {
//        //System.out.println("edit klant started");
//        System.out.println(klant.getKlantnaam());
//        if (type.equals("update")){
//            headertext.setText("Klant gegevens wijzigen");
//            //System.out.println("update selected");
//        }
//        else {
//            headertext.setText("Klant verwijderen ?");
//            //System.out.println("delete selected");
//        }
//        klantnaamField.setText(klant.getKlantnaam());
//        klantcontactpersoonField.setText(klant.getKlantcontactpersoon());
//        klantcontacttelnrField.setText(klant.getKlantcontacttelnr());
//        klantcontactemailField.setText(klant.getKlantcontactemail());
//        klantopmerkingField.setText(klant.getKlantopmerking());
//    }
//
//    public void updateKlant (Klant klant){
//        klant.setKlantnaam(klantnaamField.getText());
//        klant.setKlantcontactpersoon(klantcontactpersoonField.getText());
//        klant.setKlantcontacttelnr(klantcontacttelnrField.getText());
//        klant.setKlantcontactemail(klantcontactemailField.getText());
//        klant.setKlantopmerking(klantopmerkingField.getText());
//    }
}