package BlueMatch;

import BlueMatch.model.Aanvraag;
import BlueMatch.model.Broker;
import BlueMatch.model.Medewerker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddMedewerkerController {

    @FXML
    private ChoiceBox<String> statusmdwBox;

    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Volledig ingezet bij klant",
                    "Beschikbaar",
                    "Gedeeltelijk beschikbaar",
                    "Niet beschikbaar (Ziekte, verlof)"
            );

    @FXML
    private void initialize() {
        statusmdwBox.setItems(options);
        statusmdwBox.setValue("Beschikbaar");
    }


    @FXML
    private TextField voornaamField;
    @FXML
    private TextField achternaamField;
    @FXML
    private TextField urenperweekField;
    @FXML
    private TextField emailmedewerkerField;
    @FXML
    private TextArea opmerkingmedewerkerField;


    @FXML
    private TableView<Medewerker> medewerkerTable;
    @FXML
    private Label titelmdwlabel;



    public Medewerker getNewMedewerker() {
        titelmdwlabel.setText("Medewerker toevoegen");
        String voornaammdw = voornaamField.getText();
        String achternaammdw = achternaamField.getText();
        String urenperweekmdw = urenperweekField.getText();
        String emailmdw = emailmedewerkerField.getText();
        String opmerkingmdw = opmerkingmedewerkerField.getText();


        Medewerker newMedewerker = new Medewerker();
        newMedewerker.setVoornaam(voornaammdw);
        newMedewerker.setAchternaam(achternaammdw);
        newMedewerker.setUren(urenperweekmdw);
        newMedewerker.setStatusmdw(statusmdwBox.getValue());
        newMedewerker.setEmailmedewerker(emailmdw);
        newMedewerker.setOpmerkingmedewerker(opmerkingmdw);

        return newMedewerker;
    }
    public void editMedewerker(Medewerker medewerker, String type) {
        if (type=="update"){titelmdwlabel.setText("Medewerker wijzigen");}
        else {
            titelmdwlabel.setText("Medewerker verwijderen ?");
        }
        voornaamField.setText(medewerker.getVoornaam());
        achternaamField.setText(medewerker.getAchternaam());
        urenperweekField.setText(medewerker.getUrenperweek());
        emailmedewerkerField.setText(medewerker.getEmailmedewerker());
        opmerkingmedewerkerField.setText(medewerker.getOpmerkingmedewerker());
    }

    public void updateMedewerker (Medewerker medewerker){
        medewerker.setVoornaam(voornaamField.getText());
        medewerker.setAchternaam(achternaamField.getText());
        medewerker.setUren(urenperweekField.getText());
        medewerker.setEmailmedewerker(emailmedewerkerField.getText());
        medewerker.setOpmerkingmedewerker(opmerkingmedewerkerField.getText());
    }
}

