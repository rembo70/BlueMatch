package BlueMatch;

import BlueMatch.model.Aanbod;
import BlueMatch.model.Aanvraag;
import BlueMatch.model.Datasource;
import BlueMatch.model.Medewerker;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class addAanbiedingController {
    @FXML
    private TextField medewerkerField;
    @FXML
    private TextField tariefaanbodField;
    @FXML
    private TextField urenperweekaanbodField;
    @FXML
    private TextField statusaanbodField;

    @FXML
    private TableView<Aanbod> aanbodTable;


    @FXML
    private ChoiceBox<String> selectMedewerkerBox;
    @FXML
    private ChoiceBox<String> aanbodBox;

    public ObservableList populateMdwNameList() {
        ArrayList<String> medewerkernaamlist = new ArrayList<String>();
        ObservableList<Medewerker> medewerkerslijst = FXCollections.observableArrayList(Datasource.getInstance().queryMedewerker());

        for (Medewerker huidigemdw : medewerkerslijst) {
            medewerkernaamlist.add(huidigemdw.getVoornaam() + " " + huidigemdw.getAchternaam());
        }
        ObservableList<String> options =
                FXCollections.observableArrayList(medewerkernaamlist
                );

        return options;
    }

    @FXML
    private void initialize() {
        ObservableList<String> optionsstatus =
                FXCollections.observableArrayList(
                        "Nieuw",
                        "Uitgenodigd voor gesprek",
                        "Plaatsing",
                        "Afgewezen",
                        "Teruggetrokken",
                        "Overig"
                );

        ObservableList<String> options = populateMdwNameList();
        selectMedewerkerBox.setItems(options);
        aanbodBox.setItems(optionsstatus);
    }

    public Aanbod getNewAanbod(String idaanvraag) {
        String medewerker = selectMedewerkerBox.getValue();
        String tariefaanbod = tariefaanbodField.getText();
        String urenperweekaanbod = urenperweekaanbodField.getText();
        String statusaanbod = aanbodBox.getValue();

        Aanbod newAanbod = new Aanbod();
        System.out.println(medewerker);
        newAanbod.setRefmedewerker(medewerker);
        newAanbod.setStatusaanbod(statusaanbod);
        newAanbod.setTariefaanbod(tariefaanbod);
        newAanbod.setUrenperweekaanbod(urenperweekaanbod);
        newAanbod.setRefaanvraag(idaanvraag);

        return newAanbod;
    }
}
