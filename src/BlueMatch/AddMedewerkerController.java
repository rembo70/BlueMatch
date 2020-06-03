package BlueMatch;

import BlueMatch.model.Aanvraag;
import BlueMatch.model.Medewerker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TextField UrenPerWeekField;

    @FXML
    private TableView<Medewerker> medewerkerTable;


    public Medewerker getNewMedewerker() {
        String voornaam = voornaamField.getText();
        String achternaam = achternaamField.getText();
        String urenperweek = urenperweekField.getText();


        Medewerker newMedewerker = new Medewerker();
        newMedewerker.setVoornaam(voornaam);
        newMedewerker.setAchternaam(achternaam);
        newMedewerker.setUren(urenperweek);
        newMedewerker.setStatusmdw(statusmdwBox.getValue());

        return newMedewerker;
    }

}

