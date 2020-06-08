package BlueMatch;

import BlueMatch.model.Klant;
import BlueMatch.model.Medewerker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AddKlantController {



    @FXML
    private TextField klantnaamField;
    @FXML
    private TextField klantcontactpersoonField;
    @FXML
    private TextField klantcontacttelnrField;
    @FXML
    private TextField klantcontactemailField;
    @FXML
    private TextField klantopmerkingField;

    @FXML
    private TableView<Klant> klantTable;


    public Klant getNewKlant() {
        String klantnaam = klantnaamField.getText();
        String klantcontactpersoon = klantcontactpersoonField.getText();
        String klantcontacttelnr = klantcontacttelnrField.getText();
        String klantcontactemail = klantcontactemailField.getText();
        String klantopmerking = klantopmerkingField.getText();


        Klant newKlant = new Klant();
        newKlant.setKlantnaam(klantnaam);
        newKlant.setKlantcontactpersoon(klantcontactpersoon);
        newKlant.setKlantcontacttelnr(klantcontacttelnr);
        newKlant.setKlantcontactemail(klantcontactemail);
        newKlant.setKlantopmerking(klantopmerking);

        return newKlant;
    }

}

