package BlueMatch;

import BlueMatch.model.Broker;
import BlueMatch.model.Klant;
import BlueMatch.model.Medewerker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddBrokerController {

    @FXML
    private TextField brokernaamField;
    @FXML
    private TextField contactpersoonField;
    @FXML
    private TextField telbrokerField;
    @FXML
    private TextField emailbrokerField;
    @FXML
    private TextArea opmerkingbrokerField;

    @FXML
    private TableView<Broker> brokerTable;


    public Broker getNewBroker() {
        String brokernaam = brokernaamField.getText();
        String contactpersoon = contactpersoonField.getText();
        String telbroker = telbrokerField.getText();
        String emailbroker = emailbrokerField.getText();
        String opmerkingbroker = opmerkingbrokerField.getText();

        Broker newBroker = new Broker();
        newBroker.setBrokernaam(brokernaam);
        newBroker.setContactpersoon(contactpersoon);
        newBroker.setTelbroker(telbroker);
        newBroker.setEmailbroker(emailbroker);
        newBroker.setOpmerkingbroker(opmerkingbroker);

        return newBroker;
    }

    public void editBroker(Broker broker) {
        brokernaamField.setText(broker.getBrokernaam());
        contactpersoonField.setText(broker.getContactpersoon());
        telbrokerField.setText(broker.getTelbroker());
        emailbrokerField.setText(broker.getEmailbroker());
        opmerkingbrokerField.setText(broker.getOpmerkingbroker());
    }

    public void updateBroker (Broker broker){
        broker.setBrokernaam(brokernaamField.getText());
        broker.setContactpersoon(contactpersoonField.getText());
        broker.setTelbroker(telbrokerField.getText());
        broker.setEmailbroker(emailbrokerField.getText());
        broker.setOpmerkingbroker(opmerkingbrokerField.getText());
    }
}

