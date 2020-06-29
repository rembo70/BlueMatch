package BlueMatch;

import BlueMatch.model.Klant;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.w3c.dom.ls.LSOutput;


import javax.mail.PasswordAuthentication;
import java.awt.event.ActionEvent;
import static BlueMatch.Main.userpassword;
import static BlueMatch.Main.userEmail;


public class LoginauthController {
    @FXML
    private TextField usermailadrfield;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private Button btninloggen;
    @FXML
    private Label statusvalidatie;


    @FXML
    public Button btnlogin (javafx.event.ActionEvent event) {

        System.out.println("validating email");
        if(usermailadrfield.getText().equals("")) {
            System.out.println("geen mailadres");
        }
        else if(new SendEmailOffice365().sendEmail(usermailadrfield.getText(), passwordfield,usermailadrfield.getText(), "Melding Validate: Ingelogd onder je gebruikersnaam", "Het versturen van een email vanuit jouw account is geslaagd. <br>  Je kan deze mail negeren, indien je zelf de validatie hebt uitgevoerd.")==true){
            //System.out.println("Mail sucessfully sent");
            btninloggen.setDisable(false);
            Main.userpassword= passwordfield;
            Main.userEmail=usermailadrfield.getText();

            statusvalidatie.setText("Validatie geslaagd. Je kan verder gaan");
        }
        else{
            System.out.println("Mail not successfully sent");
            //btninloggen.setText("Ga verder zonder inloggen. (mails kunnen niet worden verstuurd)");
            //btninloggen.setDisable(true);
            statusvalidatie.setText("Validatie niet geslaagd.");
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