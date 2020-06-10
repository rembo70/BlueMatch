package BlueMatch;

import BlueMatch.model.Aanvraag;
import BlueMatch.model.Broker;
import BlueMatch.model.Datasource;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddAanvraagController {

    @FXML
    private ChoiceBox<String> statusklantBox;
    @FXML
    private DatePicker datePickAanvraagDate;
    @FXML
    private DatePicker datePickerStartDate;

    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Nieuw",
                    "Vrijblijvend aangeboden",
                    "Aangeboden Broker",
                    "Aangeboden Eindklant",
                    "Aangeboden"


            );

    @FXML
    private void initialize() {
        statusklantBox.setItems(options);
        statusklantBox.setValue("Nieuw");
    }


    @FXML
    private TextField BrokerField;
    @FXML
    private TextField ContactField;
    @FXML
    private TextField FunctieField;
    @FXML
    private TextField UrenPerWeekField;
    //    @FXML
//    private TextField StatusKlantField;
    @FXML
    private TextField DatumAanvraagField;
    @FXML
    private TextField LocatieField;
    @FXML
    private TextField StartDatumField;
    @FXML
    private TextField OpmerkingField;
    @FXML
    private TextField KlantField;
    @FXML
    private TextField LinkField;
    @FXML
    private TextField TariefField;
    @FXML
    private TableView<Aanvraag> aanvraagTable;


    public Aanvraag getNewAanvraag() {
        //SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");
        String datumaanvraag = "";
        String startdatum = "";
        String broker = BrokerField.getText();
        String contact = ContactField.getText();
        String functie = FunctieField.getText();
        String urenperweek = UrenPerWeekField.getText();
        String statusklant = statusklantBox.getValue();
        if (datePickAanvraagDate.getValue() != null) {
            datumaanvraag = datePickAanvraagDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        String locatie = LocatieField.getText();
        if (datePickerStartDate.getValue() != null) {
            startdatum = datePickerStartDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        String opmerking = OpmerkingField.getText();
        String klant = KlantField.getText();
        System.out.println("klantnaam" + klant);
        String link = LinkField.getText();
        String tarief = TariefField.getText();

        Aanvraag newAanvraag = new Aanvraag();
        System.out.println(broker);
        newAanvraag.setRefbroker(broker);
        newAanvraag.setRefcontact(contact);
        newAanvraag.setFunctie(functie);
        newAanvraag.setVraagurenweek(urenperweek);
        newAanvraag.setStatusklant(statusklant);
        newAanvraag.setDatumaanvraag(datumaanvraag);
        newAanvraag.setLocatie(locatie);
        newAanvraag.setStartdatum(startdatum);
        newAanvraag.setOpmerking(opmerking);
        newAanvraag.setRefklant(klant);
        newAanvraag.setLinkaanvraag(link);
        newAanvraag.setTariefaanvraag(tarief);
        return newAanvraag;
    }
    public void editAanvraag(Aanvraag aanvraag) {
        // headertext.setText("Aanvraag wijzigen");
        BrokerField.setText(aanvraag.getRefbroker());
        ContactField.setText(aanvraag.getRefcontact());
        FunctieField.setText(aanvraag.getFunctie());
        UrenPerWeekField.setText(aanvraag.getVraagurenweek());
        statusklantBox.setValue(aanvraag.getStatusklant());
        //datePickAanvraagDate.setValue(LocalDate.parse(aanvraag.getDatumaanvraag()));
        LocatieField.setText(aanvraag.getLocatie());
        // datePickerStartDate.setValue(LocalDate.parse(aanvraag.getStartdatum()));
        OpmerkingField.setText(aanvraag.getOpmerking());
        KlantField.setText(aanvraag.getRefklant());
        LinkField.setText(aanvraag.getLinkaanvraag());
        TariefField.setText(aanvraag.getTariefaanvraag());
    }

    public void updateAanvraag (Aanvraag aanvraag){
          aanvraag.setRefbroker(BrokerField.getText());
          aanvraag.setRefcontact(ContactField.getText());
          aanvraag.setFunctie(FunctieField.getText());
          aanvraag.setVraagurenweek(UrenPerWeekField.getText());
          aanvraag.setStatusklant(statusklantBox.getValue());
          if (datePickAanvraagDate.getValue() != null) {
              aanvraag.setDatumaanvraag(datePickAanvraagDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
          }
          aanvraag.setLocatie(LocatieField.getText());
        if (datePickerStartDate.getValue() != null) {
            aanvraag.setStartdatum(datePickerStartDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
          aanvraag.setOpmerking(OpmerkingField.getText());
          aanvraag.setRefklant(KlantField.getText());
          aanvraag.setLinkaanvraag(LinkField.getText());
          aanvraag.setTariefaanvraag(TariefField.getText());
    }
}

